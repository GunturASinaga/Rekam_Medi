const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const dbConnection = require('../db');

// GET
// fungsi untuk mengakses data alergi seorang user
router.get('/', authenticateToken, (req, res) => {
    const user_id = req.user.userId;

    const sql = "SELECT * FROM alergi WHERE user_id = ?";
    dbConnection.query(sql, [user_id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error fetching data from the database.',
                    error: error.message,
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


// fungsi yang digunakan untuk mengakses alergi tertentu
router.get('/:id', authenticateToken, (req, res) => {
    const id = req.params.id;
    const user_id = req.user.userId;

    const sql = "SELECT * FROM alergi WHERE user_id = ? and id = ?";
    dbConnection.query(sql, [user_id, id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error fetching data from the database.',
                    error: error.message,
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

// POST
// fungsi untuk menambahkan alergi
router.post('/', authenticateToken, (req, res) => {
    const user_id = req.user.userId;
    const { nama } = req.body;

    // Input validation
    if (!nama) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'nama is required.',
            },
            timestamp: new Date().toISOString(),
        });
    }

    const sql = "INSERT INTO alergi (user_id, nama) VALUES (?, ?)";
    dbConnection.query(sql, [user_id, nama], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error inserting data into the database.',
                    db_error: error.message, // Include the actual database error message
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            res.status(201).json({
                status: 'success',
                data: results,
                message: 'Data inserted successfully.',
                timestamp: new Date().toISOString(),
            });
        }
    });
});

// PUT
// fungsi untuk mengupdate data alergi seorang user
router.put('/:id', authenticateToken, (req, res) => {
    const user_id = req.user.userId;
    const id = req.params.id;
    const { nama } = req.body;

    if (!nama) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'nama is required.',
            },
            timestamp: new Date().toISOString(),
        });
    }

    const sql = "UPDATE alergi SET nama = ? WHERE id = ? AND user_id = ?";
    dbConnection.query(sql, [nama, id, user_id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error updating data in the database.',
                    error: error.message,
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            res.json({
                status: 'success',
                message: 'Data updated successfully.',
                timestamp: new Date().toISOString(),
            });
        }
    });
});

// DELETE
// fungsi yang digunakan untuk menghapus data alergi user
router.delete('/:id', authenticateToken, (req, res) => {
    const user_id = req.user.userId;
    const id = req.params.id;

    const sqlCheckExistence = "SELECT id FROM alergi WHERE id = ? AND user_id = ?";
    const sqlDelete = "DELETE FROM alergi WHERE id = ? AND user_id = ?";

    // Check if the specified record exists before attempting deletion
    dbConnection.query(sqlCheckExistence, [id, user_id], (checkError, checkResults) => {
        if (checkError) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error checking data existence in the database.',
                    error: checkError.message,
                },
                timestamp: new Date().toISOString(),
            });
        } else if (checkResults.length === 0) {
            // Record not found
            res.status(404).json({
                status: 'error',
                error: {
                    code: 404,
                    message: 'Record not found or access denied.',
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            // Record exists, proceed with deletion
            dbConnection.query(sqlDelete, [id, user_id], (deleteError, deleteResults) => {
                if (deleteError) {
                    res.status(500).json({
                        status: 'error',
                        error: {
                            code: 500,
                            message: 'Error deleting data from the database.',
                            error: deleteError.message,
                        },
                        timestamp: new Date().toISOString(),
                    });
                } else {
                    res.json({
                        status: 'success',
                        message: 'Data deleted successfully.',
                        timestamp: new Date().toISOString(),
                    });
                }
            });
        }
    });
});

// Middleware for authenticating tokens
function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (token == null) {
        return res.status(401).json({
            status: 'error',
            error: {
                code: 401,
                message: 'Unauthorized: Missing token.',
                timestamp: new Date().toISOString(),
            },
        });
    }

    jwt.verify(token, 'Hello', (err, user) => {
        if (err) {
            return res.status(403).json({
                status: 'error',
                error: {
                    code: 403,
                    message: 'Forbidden: Invalid token.',
                    timestamp: new Date().toISOString(),
                },
            });
        }

        req.user = user;
        next();
    });
}

module.exports = router;
