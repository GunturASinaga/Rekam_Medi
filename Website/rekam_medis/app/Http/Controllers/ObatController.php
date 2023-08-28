<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use GuzzleHttp\Client;

class ObatController extends Controller
{
    public function editObat($id){
        $token = session('api_token');
        $username = session('user');
        $client = new Client();
        $response = $client->get("http://localhost:3000/obat/spesifik/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
        ]);
        $data = json_decode($response->getBody(), true);
        $obat = $data['data'];
        $obat = $obat[0];

        return view('editObat')->with('obat', $obat)->with('username', $username);
    }

    public function updateObat(Request $request, $id){
        $token = session('api_token');
        $client = new Client();

        $response = $client->put("http://localhost:3000/obat/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
            'json' => [
                'nama_obat' => $request->input('nama_obat'),
                // Include other fields you want to update here
            ],
        ]);

        return redirect('/penyakit'); //
    }

    public function tambahObat($id){
        $username = session('user');
        return view('tambahObat')->with('id',$id)->with('username', $username);
    }

    public function tambahObatProcess(Request $request, $id){
        $token = session('api_token');
        $client = new Client();

        $response = $client->post("http://localhost:3000/obat/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
            'json' => [
                'nama_obat' => $request->input('nama_obat'),
            ],
        ]);
        return redirect('/penyakit'); //
    }

    public function hapusObat($id){
        $token = session('api_token');
        $client = new Client();
        $response = $client->delete("http://localhost:3000/obat/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
        ]);
        return redirect('/penyakit'); //
    }

}
