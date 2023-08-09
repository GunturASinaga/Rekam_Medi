const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken');
const dbConnection = require('../db');


router.post('/coba', authenticateToken, (req, res) => {
    res.json(
        {
            username : req.user.username,
            id: req.user.userId, 
        }
    ); // Use req.user.username to access the user's username
});

function authenticateToken(req, res, next){
    const authHeader = req.headers['authorization']
    const token = authHeader && authHeader.split(' ')[1]
    if(token == null) return res.sendStatus(401)

    jwt.verify(token, 'Hello', (err, user) =>{
        if (err) return res.sendStatus(403)
        req.user = user
        next()
    })
}

module.exports = router;