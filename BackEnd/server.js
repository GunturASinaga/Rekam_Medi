const express = require('express');

const jwt = require('jsonwebtoken');
const dbConnection = require('./db');
const autod = require('./routes/authenticate');
const dataRet = require('./routes/retrieveData');
const dataPenyakit = require('./routes/penyakit');
const dataObat = require('./routes/obat');
const dataAlergi = require('./routes/alergi');
const app = express();
app.use(express.json());

//routing
app.use('/auth', autod)
app.use('/data', dataRet)
app.use('/penyakit', dataPenyakit)
app.use('/obat', dataObat)
app.use('/alergi', dataAlergi)

//GET
app.get('/', authenticateToken, (req, res) => {
  const id = req.user.userId;

  const sqlPenyakit = "SELECT * FROM penyakit where user_id = ?";
  const sqlAlergi = "SELECT * FROM alergi where user_id = ?";

  dbConnection.query(sqlPenyakit, [id], (error, penyakitResults) => {
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

      // Execute the query to fetch alergi data
      dbConnection.query(sqlAlergi, [id], (alergiError, alergiResults) => {
          if (alergiError) {
              return res.status(500).json({
                  status: 'error',
                  error: {
                      code: 500,
                      message: 'Error fetching alergi data from the database.',
                  },
                  timestamp: new Date().toISOString(),
              });
          }

          res.json({
              status: 'success',
              penyakit: penyakitResults,
              alergi: alergiResults,
              message: 'User data retrieved successfully.',
              timestamp: new Date().toISOString(),
          });
      });
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

// Start the server
const port = process.env.PORT ||3000;
app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});