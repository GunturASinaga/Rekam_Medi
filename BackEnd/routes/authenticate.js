const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken');
const dbConnection = require('../db');

router.get('/users', (req, res) => {
    const sql = 'SELECT id, username, birth_date, alamat, kontak FROM users';
    dbConnection.query(sql, (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error fetching data from the database.',
                    error : error.message
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            res.json({
                status: 'success',
                data: results,
                message: 'User data retrieved successfully.',
                timestamp: new Date().toISOString(),
            });
        }
    });
});

//REGISTER
router.post('/register', async (req, res) => {
    try {
        const { username, birth_date, alamat, kontak, password } = req.body;

        // Verify if the username is unique
        const checkUsernameSql = 'SELECT * FROM users WHERE username = ?';
        dbConnection.query(checkUsernameSql, [username], async (usernameError, usernameResults) => {
            if (usernameError) {
                return res.status(500).json({
                    status: 'error',
                    error: {
                        code: 500,
                        message: 'Error checking username uniqueness.',
                    },
                    timestamp: new Date().toISOString(),
                });
            }

            if (usernameResults.length > 0) {
                return res.status(409).json({
                    status: 'error',
                    error: {
                        code: 409,
                        message: 'Username already exists.',
                    },
                    timestamp: new Date().toISOString(),
                });
            }

            // Verify password strength
            if (password.length < 8 || !/[a-zA-Z]/.test(password) || !/\d/.test(password)) {
                return res.status(400).json({
                    status: 'error',
                    error: {
                        code: 400,
                        message: 'Password must be at least 8 characters long and contain at least one letter and one number.',
                    },
                    timestamp: new Date().toISOString(),
                });
            }

            // Hash the provided password
            const hashedPassword = await bcrypt.hash(password, 10);

            // Insert user data into the 'user' table
            const insertUserSql = 'INSERT INTO users (username, birth_date, alamat, kontak, password) VALUES (?, ?, ?, ?, ?)';
            dbConnection.query(insertUserSql, [username, birth_date, alamat, kontak, hashedPassword], (error, results) => {
                if (error) {
                    res.status(500).json({
                        status: 'error',
                        error: {
                            code: 500,
                            message: 'Error inserting data into the database.',
                        },
                        timestamp: new Date().toISOString(),
                    });
                } else {
                    const userId = results.insertId;
                    const user = {
                        id: userId,
                        username,
                        birth_date,
                        alamat,
                        kontak,
                    };

                    res.status(201).json({
                        status: 'success',
                        message: 'User registered successfully.',
                        data: user,
                        timestamp: new Date().toISOString(),
                    });
                }
            });
        });
    } catch (error) {
        res.status(500).json({
            status: 'error',
            error: {
                code: 500,
                message: 'Internal server error.',
            },
            timestamp: new Date().toISOString(),
        });
    }
});


//LOGIN
router.post('/login', async (req, res) => {
    const { username, password } = req.body;

    // Query the database to find the user based on the username
    const getUserSql = 'SELECT * FROM users WHERE username = ?';
    dbConnection.query(getUserSql, [username], async (error, results) => {
        if (error) {
            return res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error fetching data from the database.',
                },
                timestamp: new Date().toISOString(),
            });
        }

        const user = results[0]; // Assuming username is unique

        if (!user) {
            return res.status(404).json({
                status: 'error',
                error: {
                    code: 404,
                    message: 'User not found.',
                },
                timestamp: new Date().toISOString(),
            });
        }

        try {
            if (await bcrypt.compare(password, user.password)) {
                // Password matches, generate and return a JWT token
                const token = jwt.sign(
                    { userId: user.id, username: user.username },
                    'Hello', // Replace this with your own secret key
                    { expiresIn: '1h' }
                );

                return res.json({
                    status: 'success',
                    message: 'Authentication successful.',
                    token,
                    timestamp: new Date().toISOString(),
                });
            } else { //password salah
                return res.status(401).json({
                    status: 'error',
                    error: {
                        code: 401,
                        message: 'Incorrect password.',
                    },
                    timestamp: new Date().toISOString(),
                });
            }
        } catch { // masalah internal
            return res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Internal server error.',
                },
                timestamp: new Date().toISOString(),
            });
        }
    });
});

module.exports = router;
