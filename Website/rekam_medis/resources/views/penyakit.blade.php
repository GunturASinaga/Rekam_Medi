<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous">
    </script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <title>Penyakit</title>
</head>

<body>
    <nav class="navbar navbar-expand navbar-dark bg-dark">
        <div class="container">
            <a href="/" class="navbar-brand">Rekam Medis</a>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a href="/alergi" class="nav-link ">Alergi</a>
                </li>

                <li class="nav-item">
                    <a href="#" class="nav-link active">Penyakit</a>
                </li>
                <li class="nav-item">
                    <a href="/" class="nav-link active"> | <i class="bi bi-person light"></i>
                        {{ $username }}</a>
                </li>
                <li class="nav-item">
                    <a href="/logout" class="btn btn-danger">Log Out</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center my-3">
            <h1>Penyakit </h1>
            <a href="tambahPenyakit" class="btn btn-success">Tambah Penyakit</a>
        </div>

        @foreach ($penyakit as $sakit)
            <div class="row justify-content-md-center">
                <div class="">
                    @php
                    $durasi = $sakit['tanggal_mulai'];
                    if ($sakit['tanggal_selesai'] == null) {
                        $durasi .= ' - sekarang';
                    } else {
                        $durasi .= ' - ' . $sakit['tanggal_selesai'];
                    }
                @endphp
                <div class="card mb-3 ">
                    <div class="d-flex justify-content-between">

                        <div class="card-body ">
                            <h3 class="card-title">{{ $sakit['nama'] }}</h5>
                                @php
                                    $durasi = $sakit['tanggal_mulai'];
                                    if ($sakit['tanggal_selesai'] == null) {
                                        $durasi .= ' - sekarang';
                                    } else {
                                        $durasi .= ' - ' . $sakit['tanggal_selesai'];
                                    }
                                @endphp
                                <h6 class="card-subtitle mb-2 " style="color:grey">{{ $durasi }}</h6>
                                <p class="card-text">Gejala : {{ $sakit['gejala'] }}</p>
                                @if ($sakit['hasil'])
                                    <p>Hasil : {{ $sakit['hasil'] }}</p>
                                @endif
                                <p class="card-text">Obat : </p>
                                <ul>
                                    @foreach ($sakit['obat'] as $obat)
                                        <li class="mb-1">
                                            {{ $obat['nama_obat'] }}
                                            <div class="btn-group">
                                                <a href="/editObat/{{ $obat['id'] }}" class="btn btn-sm btn-success ">edit</a>
                                                <a href="/hapusObat/{{ $obat['id'] }}" class="btn btn-sm btn-danger">Hapus</a>
                                            </div>
                                        </li>
                                    @endforeach
                                </ul>
                                <a href="/tambahObat/{{ $sakit['id'] }}" class="btn btn-sm btn-outline-secondary">Tambah Obat</a>
                        </div>
                        <div>
                            <div class="card-body">
                                <a href="/editPenyakit/{{ $sakit['id'] }}" class="btn btn-warning" style="width: 6em">Edit</a>
                                <a href="/hapusPenyakit/{{ $sakit['id'] }}" class="btn btn-danger" style="width: 6em">Hapus</a>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        @endforeach
    </div>

</body>

</html>
