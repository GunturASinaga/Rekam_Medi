const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken');
const dbConnection = require('../db');


//GET
router.get('/:id', authenticateToken, (req, res) => {
    const penyakit_id = req.params.id;

    // Validate penyakit_id as an integer
    if (!Number.isInteger(Number(penyakit_id))) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'Invalid penyakit_id. It must be a valid integer.',
                timestamp: new Date().toISOString(),
            },
        });
    }

    const sql = "SELECT * FROM obat WHERE penyakit_id = ?";

    dbConnection.query(sql, [penyakit_id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error fetching data from the database.',
                    db_error: error.message, // Include the database error message
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            res.json({
                status: 'success',
                data: results,
                message: 'Data retrieved successfully.',
                timestamp: new Date().toISOString(),
            });
        }
    });
});

// POST
router.post('/:id', authenticateToken, (req, res) => {
    const penyakit_id = req.params.id;
    const nama_obat = req.body.nama_obat;

    // Validate input
    if (!penyakit_id || !nama_obat) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'Both penyakit_id and nama_obat are required.',
                timestamp: new Date().toISOString(),
            },
        });
    }

    const sql = "INSERT INTO `obat` (`penyakit_id`, `nama_obat`) VALUES (?, ?)";
    dbConnection.query(sql, [penyakit_id, nama_obat], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error inserting data into the database.',
                    db_error: error.message, // Include the database error message
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            res.json({
                status: 'success',
                data: results,
                message: 'Data inserted successfully.',
                timestamp: new Date().toISOString(),
            });
        }
    });
});

// PUT
router.put('/:id', authenticateToken, (req, res) => {
    const obat_id = req.params.id;
    const nama_obat = req.body.nama_obat;

    // Validate input
    if (!obat_id || !nama_obat) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'Both obat_id and nama_obat are required.',
                timestamp: new Date().toISOString(),
            },
        });
    }

    const sql = "UPDATE obat SET nama_obat = ? WHERE id = ?";
    dbConnection.query(sql, [nama_obat, obat_id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error updating data in the database.',
                    db_error: error.message, // Include the database error message
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            if (results.affectedRows === 0) {
                // No rows were affected, resource wasn't modified
                return res.status(204).send();
            }

            res.json({
                status: 'success',
                data: results,
                message: 'Data updated successfully.',
                timestamp: new Date().toISOString(),
            });
        }
    });
});

//DELETE
router.delete('/:id', authenticateToken, (req, res) => {
    const obat_id = req.params.id;

    // Validate input
    if (!obat_id) {
        return res.status(400).json({
            status: 'error',
            error: {
                code: 400,
                message: 'obat_id is required.',
                timestamp: new Date().toISOString(),
            },
        });
    }

    const sql = "DELETE FROM obat WHERE id = ?";
    dbConnection.query(sql, [obat_id], (error, results) => {
        if (error) {
            res.status(500).json({
                status: 'error',
                error: {
                    code: 500,
                    message: 'Error deleting data from the database.',
                    db_error: error.message, // Include the database error message
                },
                timestamp: new Date().toISOString(),
            });
        } else {
            if (results.affectedRows === 0) {
                // No rows were affected, resource wasn't found
                return res.status(404).json({
                    status: 'error',
                    error: {
                        code: 404,
                        message: 'Resource not found.',
                        timestamp: new Date().toISOString(),
                    },
                });
            }

            res.json({
                status: 'success',
                message: 'Data deleted successfully.',
                timestamp: new Date().toISOString(),
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