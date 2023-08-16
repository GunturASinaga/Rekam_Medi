<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class PenyakitController extends Controller
{
    public function penyakit(){
        $token = session('api_token');
        $username = session('user');

        if($token != null){
            return view('userToken')
                        ->with('username', $username)
                        ->with('token', $token);
        } else{
            return view('welcome');
        }
    }


}
