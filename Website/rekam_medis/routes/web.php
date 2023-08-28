<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Controllers\PenyakitController;
use App\Http\Controllers\AlergiController;
use App\Http\Controllers\ObatController;


// homepage
Route::get('/', [UserController::class, 'homepage']);

Route::get('/user', [UserController::class, 'getData']);

//LOGIN
Route::post('/login', [UserController::class, 'loginSubmit'])->name('login.submit');
Route::get('/login', function () {return view('login');});

//REGISTER
Route::get('/register', function () {return view('register')->with('message', "process");});
Route::post('/registerSubmit', [UserController::class, 'registerSubmit']);

//LogOut
Route::get('/logout', [UserController::class, 'logout']);

//Alergi
Route::get('/alergi', [AlergiController::class, 'alergi']);
Route::get('/alergiEdit/{id}', [AlergiController::class, 'alergiEdit']);
Route::put('/alergiEditProcess', [AlergiController::class, 'alergiEditProcess']);
Route::get('tambahAlergi', [AlergiController::class, 'tambahAlergi']);
Route::post('/alergiTambahProcess', [AlergiController::class, 'alergiTambahProcess']);
Route::get('/alergiHapus/{id}', [AlergiController::class, 'alergiHapus']);

//Penyakit
Route::get('/penyakit', [PenyakitController::class, 'penyakit']);
Route::get('/editPenyakit', [PenyakitController::class, 'editPenyakit']);
Route::get('/tambahPenyakit', [PenyakitController::class, 'tambahPenyakit']);
Route::post('/tambahPenyakitProcess', [PenyakitController::class, 'tambahPenyakitProcess']);
Route::get('/hapusPenyakit/{id}', [PenyakitController::class, 'hapusPenyakit']);
Route::get('/editPenyakit/{id}', [PenyakitController::class, 'editPenyakit']);
Route::post('/editPenyakitProcess', [PenyakitController::class, 'editPenyakitProcess']);

//obat
Route::get('/editObat/{id}', [ObatController::class, 'editObat']);
Route::post('/updateObat/{id}', [ObatController::class, 'updateObat']);
Route::get('/tambahObat/{id}', [ObatController::class, 'tambahObat']);
Route::post('/tambahObatProcess/{id}', [ObatController::class, 'tambahObatProcess']);
Route::get('/hapusObat/{id}', [ObatController::class, 'hapusObat']);

