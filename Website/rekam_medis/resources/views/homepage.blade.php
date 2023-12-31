<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <title>Rekam Medis</title>
</head>
<body>
    <nav class="navbar navbar-expand navbar-dark bg-dark">
        <div class="container">
            <a href="/" class="navbar-brand" >Rekam Medis</a>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a href="/alergi" class="nav-link ">Alergi</a>
                </li>
                <li class="nav-item">
                    <a href="/penyakit" class="nav-link ">Penyakit</a>
                </li>
                <li class="nav-item">
                    <a href="/" class="nav-link active"> | <i class="bi bi-person light"></i> {{ $username }}</a>
                </li>
                <li class="nav-item">
                    <a href="/logout" class="btn btn-danger">Log Out</a>
                </li>
            </ul>
        </div>
    </nav>



    <div class="container">
        <h1>Penyakit</h1>
        <div class="row">
            @foreach ($penyakit as $sakit)
            <div class="col-3 offset-1">
                <div class="card mb-3">
                    <div class="card-body ">
                        <h5 class="card-title">{{ $sakit['nama'] }}</h5>
                        @php
                        $durasi = $sakit['tanggal_mulai'];
                        if ($sakit['tanggal_selesai'] == null) {
                            $durasi .= " - sekarang";
                        } else {
                            $durasi .= " - " . $sakit['tanggal_selesai'];
                        }
                        @endphp
                        <p class="card-text">{{ $durasi }}</p>
                    </div>
            </div>
            </div>
        @endforeach
        </div>
    </div>


    <div class="container">
        <h3>Alergi</h3>
        <ul>
            @foreach ($alergi as $aler)
            <li>{{ $aler['nama'] }}</li>
            @endforeach
        </ul>
    </div>
</body>
</html>
