<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\Http;

use Illuminate\Http\Request;
use GuzzleHttp\Client;

class UserController extends Controller
{


    public function loginSubmit(Request $request)
    {
        $username = $request->input('username');
        $password = $request->input('password');

        // Make the API request with the collected username and password
        $client = new Client();
        $response = $client->post('http://localhost:3000/auth/login', [
            'json' => [
                'username' => $username,
                'password' => $password,
            ],
        ]);

        $data = json_decode($response->getBody(), true);

        // Process the response data as needed
        $token = $data['token'];
        $username = $data['username'];

        // Store the token in the session
        session(['api_token' => $token]);
        session(['user' => $username]);

        // Process the response data as needed
        return redirect('/');
    }

    public function homePage(){
        $token = session('api_token');
        $username = session('user');

        if($token != null){
            $client = new Client();
            $response = $client->get('http://localhost:3000/', [
                'headers' => [
                    'Authorization' => 'Bearer ' . $token,
                ],
            ]);

            $data = json_decode($response->getBody(), true);
            if($data['status'] == 'success'){

                //perlu ada durasi di sini

                return view('homepage')->with('username', $username)->with('alergi', $data['alergi'])->with('penyakit', $data['penyakit']);
            }
        } else{
            return view('welcome');
        }
    }

    public function logout(){
         // Remove the token from the session
        session()->forget('api_token');

        // You might also want to clear the entire session
        session()->flush();

        return redirect('/');
    }
}
