<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use GuzzleHttp\Client;

class AlergiController extends Controller
{
    public function alergi(){
        $token = session('api_token');
        $username = session('user');
        $client = new Client();
        $response = $client->get('http://localhost:3000/alergi', [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
        ]);

        $data = json_decode($response->getBody(), true);
        if($data['status'] == 'success'){
            $alergi = $data['data'];

            return view('alergi')->with('username', $username)->with('alergi', $alergi);
        }
    }

    public function alergiEdit($id){
        $token = session('api_token');
        $username = session('user');
        $client = new Client();
        $response = $client->get("http://localhost:3000/alergi/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
        ]);

        $data = json_decode($response->getBody(), true); // Decode the JSON response
        $alergi = $data['data'] ?? null; // Check if 'data' key exists in the response
        $alergi = $alergi[0];

        if ($alergi != null) {
            return view('alergiEdit', ['alergi' => $alergi])->with('username', $username); // Pass the 'alergi' variable to the view
        } else {
            // Handle the case when 'alergi' is not found, maybe show an error message
            return redirect()->back()->with('error', 'Alergi not found.');
        }
    }

    public function alergiHapus($id){
        $token = session('api_token');
        $username = session('user');
        $client = new Client();
        $response = $client->delete("http://localhost:3000/alergi/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
        ]);
        return redirect('/alergi');
    }

    public function alergiEditProcess(Request $request){
        $token = session('api_token');
        $client = new Client();
        $id = $request -> input('id');

        $response = $client->put("http://localhost:3000/alergi/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
            'json' => [
                'nama' => $request->input('nama'),
            ],
        ]);
        return redirect('/alergi');
    }

    public function tambahAlergi(){
        return view('tambahAlergi');
    }

    public function alergiTambahProcess(Request $request){
        $token = session('api_token');
        $client = new Client();

        $data = [
            'nama' => $request->input('nama'),
            'gejala' => $request->input('gejala'),
            'tanggal_mulai' => $request->input('tanggal_mulai'),
            'tindakan_medis' => $request->input('tindakan_medis'),
            'hasil' => $request->input('hasil'),
        ];

        if ($request->has('sudah_selesai') && $request->input('sudah_selesai') == 1) {
            $data['tanggal_selesai'] = $request->input('tanggal_selesai');
        }

        $response = $client->post("http://localhost:3000/alergi", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
            'json' => $data,
        ]);

        return redirect('/alergi')->with('success', 'Alergi added successfully.');
    }



}
