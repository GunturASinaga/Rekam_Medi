<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Rekam Medis</title>
</head>
<body>
    <h1>{{ $username }}</h1>
    <ul>
        @foreach ($penyakit as $sakit)
        <li>Nama : {{ $sakit['nama'] }} <br>
            Durasi {{ $sakit['tanggal_mulai']}} - {{ $sakit['tanggal_selesai'] }}
        </li>
        @endforeach
    </ul>

    <h3>Alergi</h3>
    <ul>
        @foreach ($alergi as $aler)
        <li>{{ $aler['nama'] }}</li>
        @endforeach
    </ul>

    <a href="/alergi"><button type="button">Alergi</button></a>
    <a href="/penyakit"><button type="button">Penyakit</button></a>
    <a href="/logout"><button type="button">Logout</button></a>
</body>
</html>
