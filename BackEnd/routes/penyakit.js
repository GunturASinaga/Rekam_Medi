const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const dbConnection = require('../db');


//GET
router.get('/', authenticateToken, (req, res) => {
    const id = req.user.userId;
    const sql = "SELECT * FROM penyakit where user_id = ?"
    dbConnection.query(sql, [id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error fetching data from the database.',
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
router.post('/', authenticateToken, (req, res) => {
    const user_id = req.user.userId;
    const { nama, gejala, tanggal_mulai, tanggal_selesai, tindakan_medis, hasil } = req.body;

    // Input validation
    if (!nama || !user_id || !gejala || !tanggal_mulai || !tindakan_medis || !hasil) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'All fields are required.',
            },
            timestamp: new Date().toISOString(),
        });
    }

    const sql = "INSERT INTO penyakit (nama, user_id, gejala, tanggal_mulai, tanggal_selesai, tindakan_medis, hasil) VALUES (?, ?, ?, ?, ?, ?, ?)";

    // Using transactions for database write operations
    dbConnection.beginTransaction(function (err) {
        if (err) {
            return res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error initiating database transaction.',
                },
                timestamp: new Date().toISOString(),
            });
        }

        dbConnection.query(sql, [nama, user_id, gejala, tanggal_mulai, tanggal_selesai, tindakan_medis, hasil], (error, results) => {
            if (error) {
                dbConnection.rollback(function () {
                    res.status(500).json({
                        status: 'error',
                        error: {
                            code: 500,
                            message: 'Error inserting data into the database.',
                            db_error: error.message, // Include the actual database error message
                        },
                        timestamp: new Date().toISOString(),
                    });
                });
            } else {
                dbConnection.commit(function (commitErr) {
                    if (commitErr) {
                        return dbConnection.rollback(function () {
                            res.status(500).json({
                                status: 'error',
                                error: {
                                    code: 500,
                                    message: 'Error committing database transaction.',
                                },
                                timestamp: new Date().toISOString(),
                            });
                        });
                    }

                    res.status(201).json({
                        status: 'success',
                        data: results,
                        message: 'User data inserted successfully.',
                        timestamp: new Date().toISOString(),
                    });
                });
            }
        });
    });
});

//PUT
router.put('/:id', authenticateToken, (req, res) => {
    const user_id = req.user.userId;
    const id = req.params.id;
    const {nama, gejala, tanggal_mulai, tanggal_selesai, tindakan_medis, hasil} = req.body;

    if (!nama || !user_id || !gejala || !tanggal_mulai || !tindakan_medis || !hasil) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'All fields are required.',
            },
            timestamp: new Date().toISOString(),
        });
    }

    const sql = "UPDATE penyakit SET nama = ?, gejala = ?, tanggal_mulai = ?, tanggal_selesai = ?, tindakan_medis = ?, hasil = ? WHERE id = ? AND user_id = ?";
    dbConnection.query(sql, [nama, gejala, tanggal_mulai, tanggal_selesai, tindakan_medis, hasil, id, user_id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error updating data in the database.',
                    error: error.message, // Include the database error message
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

//DELETE
router.delete('/:id', authenticateToken, (req, res) => {
    const user_id = req.user.userId;
    const id = req.params.id;

    const sqlCheckExistence = "SELECT id FROM penyakit WHERE id = ? AND user_id = ?";
    const sqlDelete = "DELETE FROM penyakit WHERE id = ? AND user_id = ?";

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