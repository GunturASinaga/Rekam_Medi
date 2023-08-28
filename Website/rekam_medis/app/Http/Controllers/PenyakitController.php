<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use GuzzleHttp\Client;


class PenyakitController extends Controller
{
    public function penyakit(){
        $token = session('api_token');
        $username = session('user');

        if($token != null){
            $client = new Client();
            $response = $client->get('http://localhost:3000/penyakit/lengkap', [
                'headers' => [
                    'Authorization' => 'Bearer ' . $token,
                ],
            ]);

            $data = json_decode($response->getBody(), true);
            if($data['status'] == 'success'){

                //perlu ada durasi di sini
                $penyakit = $data['data'];

                return view('penyakit')->with('username', $username)->with('penyakit', $penyakit);
            }
        } else{
            return view('welcome');
        }
    }

    public function editPenyakit($id){
        $token = session('api_token');
        $username = session('user');
        $client = new Client();
        $response = $client->get("http://localhost:3000/penyakit/id/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
        ]);
        $data = json_decode($response->getBody(), true);
        $penyakit = $data['data'];
        $penyakit = $penyakit[0];

        return view('editPenyakit')->with('penyakit', $penyakit)->with('username', $username);
    }

    public function editPenyakitProcess(Request $request){
        $token = session('api_token');
        $client = new Client();

        $id = $request->input('id'); // Get the ID from the hidden field

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

        $response = $client->put("http://localhost:3000/penyakit/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
            'json' => $data,
        ]);

        // Handle the API response and potential errors here
        return redirect('/penyakit')->with('success', 'Penyakit edited successfully.');
    }


    public function tambahPenyakit(){
        $username = session('user');
        return view('tambahPenyakit')->with('username', $username);
    }

    public function tambahPenyakitProcess(Request $request){
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

        $response = $client->post("http://localhost:3000/penyakit", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
            'json' => $data,
        ]);

        return redirect('/penyakit');
    }


    public function hapusPenyakit($id){
        $token = session('api_token');
        $client = new Client();
        $response = $client->delete("http://localhost:3000/penyakit/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
        ]);
        return redirect('/penyakit');
    }
}
