<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Controllers\PenyakitController;
use App\Http\Controllers\AlergiController;
use App\Http\Controllers\ObatController;


//ROUTING

// homepage route
Route::get('/', [UserController::class, 'homepage']);

// Merupakan route untuk mendapatkan data user
Route::get('/user', [UserController::class, 'getData']);

//LOGIN ROUTE
Route::post('/login', [UserController::class, 'loginSubmit'])->name('login.submit'); // tampilan dari login
Route::get('/login', function () {return view('login');}); // proses login

//REGISTER ROUTE
Route::get('/register', function () {return view('register')->with('message', "process");}); // tampilan dari registe
Route::post('/registerSubmit', [UserController::class, 'registerSubmit']); // proses register

//LOGOUT ROUTE
Route::get('/logout', [UserController::class, 'logout']);

//ALERGI ROUTE
Route::get('/alergi', [AlergiController::class, 'alergi']); // tampilan dari data data alergi
Route::get('/alergiEdit/{id}', [AlergiController::class, 'alergiEdit']); // melakuan edit erhadp id yang diterima
Route::put('/alergiEditProcess', [AlergiController::class, 'alergiEditProcess']); // melakukan proses edit terhadap alergi
Route::get('tambahAlergi', [AlergiController::class, 'tambahAlergi']); // menampilkan tampilan untuk menambah alergi
Route::post('/alergiTambahProcess', [AlergiController::class, 'alergiTambahProcess']); // memberikan tampilan untuk menambahkan alergi
Route::get('/alergiHapus/{id}', [AlergiController::class, 'alergiHapus']); // melakukan proses penhapusan alergi

//PENYAKIT ALERGI
Route::get('/penyakit', [PenyakitController::class, 'penyakit']); // menampilkan penyakit dan informasi terkait
Route::get('/editPenyakit', [PenyakitController::class, 'editPenyakit']); // menampilkan tampilan edit penyakit dan data yang akan di edit
Route::get('/tambahPenyakit', [PenyakitController::class, 'tambahPenyakit']); // menampilkan tampilan untuk menambah penyakit
Route::post('/tambahPenyakitProcess', [PenyakitController::class, 'tambahPenyakitProcess']); // melakukan proses penambahan penyakit
Route::get('/hapusPenyakit/{id}', [PenyakitController::class, 'hapusPenyakit']); // menghapus data penyakit
Route::get('/editPenyakit/{id}', [PenyakitController::class, 'editPenyakit']); // menamilkan tampilan edit penyakit dan data yang akan diedit
Route::post('/editPenyakitProcess', [PenyakitController::class, 'editPenyakitProcess']); // melakukan proses untuk pengeditan penyakit

//obat
Route::get('/editObat/{id}', [ObatController::class, 'editObat']); // menampilkan menu untuk mengendit penyakit dan data-data yang akan diedit
Route::post('/updateObat/{id}', [ObatController::class, 'updateObat']); // melakukan update terhadap data obat yang dgiunakan
Route::get('/tambahObat/{id}', [ObatController::class, 'tambahObat']); // menampilkan menu untuk menambah obat
Route::post('/tambahObatProcess/{id}', [ObatController::class, 'tambahObatProcess']); // memproses penambahan obat
Route::get('/hapusObat/{id}', [ObatController::class, 'hapusObat']); // menghapus data obat tertentu di database

