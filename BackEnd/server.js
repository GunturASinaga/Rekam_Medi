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

// Start the server
const port = process.env.PORT ||3000;
app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});