<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Controllers\PenyakitController;


// homepage
Route::get('/', [UserController::class, 'homepage']);

Route::get('/user', [UserController::class, 'getData']);

//LOGIN
Route::post('/login', [UserController::class, 'loginSubmit'])->name('login.submit');

Route::get('/login', function () {
    return view('login');
});

//LogOut
Route::get('/logout', [UserController::class, 'logout']);

//Register

Route::get('/penyakit', [PenyakitController::class, 'penyakit']);

Route::get('/logout', [PenyakitController::class, 'logout']);
