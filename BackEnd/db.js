const mysql = require('mysql2');

// Replace the placeholders with your MySQL database credentials
const dbConnection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'rekam_medis',
});

module.exports = dbConnection;
