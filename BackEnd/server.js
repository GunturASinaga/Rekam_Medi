const express = require('express'); // Import framework Express

const jwt = require('jsonwebtoken'); // Import JSON Web Token (JWT) library
const dbConnection = require('./db'); // Import a database connection module
const autod = require('./routes/authenticate'); // Import authentication routes
const dataRet = require('./routes/retrieveData');// Import data retrieval routes    
const dataPenyakit = require('./routes/penyakit');// Import routes for 'penyakit' data
const dataObat = require('./routes/obat');// Import routes for 'obat' data
const dataAlergi = require('./routes/alergi');// Import routes for 'alergi' data
const app = express();
app.use(express.json());

//routing sesuai dengan fungsinya
app.use('/auth', autod) // routing untuk fungsi authentication
app.use('/data', dataRet) // routing untuk fungsi mengolah data pengguna
app.use('/penyakit', dataPenyakit) // routing untuk mengolah data penyakit
app.use('/obat', dataObat) // routing untuk mengolah data obat
app.use('/alergi', dataAlergi) // routing untuk mengolah data alergi

//GET
// fungsi untuk mengambil data penyakit dan data alergi dari database sesuai dengan user yang terauthentikasi berdasarkan token yang digunakan
app.get('/', authenticateToken, (req, res) => {
    const id = req.user.userId;
  

    // sql syntax untuk mengambil dari database
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


// Fungsi untuk melakukan authentikasi token yang diterima
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