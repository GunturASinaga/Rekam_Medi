<?php
// Obat controller berfungsi untuk mengatur tampilan menu obat, dan juga menampilkan data, serta bertanggungjawab dalam proses tambah, edit, dan juga menghapus data penyakit
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use GuzzleHttp\Client;

class ObatController extends Controller
{
    public function editObat($id){ // fungsi untuk melakukan proses edit penyakit
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

    public function updateObat(Request $request, $id){ // fungsi untuk mengubah data obat ,berrdasarkan id obat tertentu
        $token = session('api_token');
        $client = new Client();

        $response = $client->put("http://localhost:3000/obat/$id", [
            'headers' => [
                'Authorization' => 'Bearer ' . $token,
            ],
            'json' => [
                'nama_obat' => $request->input('nama_obat'),
            ],
        ]);

        return redirect('/penyakit'); //
    }

    public function tambahObat($id){ // untuk menampilkan tampilan penambahan obat
        $username = session('user');
        return view('tambahObat')->with('id',$id)->with('username', $username);
    }

    public function tambahObatProcess(Request $request, $id){ // untuk melakukan proses penambahan obat
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

    public function hapusObat($id){ // untuk melakukan proses penghapusan obat
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
