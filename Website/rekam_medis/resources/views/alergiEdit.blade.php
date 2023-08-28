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
    <title>Alergi Edit</title>
</head>
<body>
    <nav class="navbar navbar-expand navbar-dark bg-dark">
        <div class="container">
            <a href="/" class="navbar-brand" >Rekam Medis</a>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a href="/alergi" class="nav-link active">Alergi</a>
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
        <h1>Edit Alergi</h1>
        <div class="form-floating">
            <form action="/alergiEditProcess" method="POST">
                @csrf
                @method('PUT')

                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="id" name="id" value="{{ $alergi['id'] }}" readonly hidden>
                    <label for="id">ID</label>
                </div>

                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="nama" name="nama" value="{{ $alergi['nama'] }}">
                    <label for="nama">Nama</label>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>

    </div>
</body>
</html>
