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
    <title>Tambah Penyakit</title>
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
                    <a href="#" class="nav-link active">penyakit</a>
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
        <h1>Tambah Penyakit</h1>
        <div class="form-floating ">
            <form action="/tambahPenyakitProcess" method="POST">
                @csrf

                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="nama" name="nama" placeholder="Place something here" required>
                    <label for="nama">Nama Disease</label>
                </div>


                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="gejala" name="gejala" placeholder="Place something here" required>
                    <label for="gejala">Gejala</label>
                </div>


                <div class="form-floating my-3">
                    <input type="date" class="form-control" id="tanggal_mulai" name="tanggal_mulai" required>
                    <label for="tanggal_mulai">Tanggal Mulai</label>
                </div>


                <div class="form-floating my-3">
                    <textarea class="form-control" id="tindakan_medis" name="tindakan_medis" rows="4" placeholder="Place something here" required></textarea>
                    <label for="tindakan_medis">Tindakan Medis</label>
                </div>


                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="hasil" name="hasil" placeholder="Place something here" required>
                    <label for="hasil">Hasil</label>
                </div>


                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="sudah_selesai" name="sudah_selesai" value="1">
                    <label class="form-check-label" for="sudah_selesai">Sudah Selesai?</label>
                </div>

                <div id="tanggal_selesai_container" style="display: none;">

                    <div class="form-floating my-3">
                        <input type="date" class="form-control" id="tanggal_selesai" name="tanggal_selesai">
                        <label for="tanggal_selesai">Tanggal Selesai</label>
                    </div>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        const sudahSelesaiCheckbox = document.getElementById('sudah_selesai');
        const tanggalSelesaiContainer = document.getElementById('tanggal_selesai_container');
        const tanggalSelesaiInput = document.getElementById('tanggal_selesai');

        sudahSelesaiCheckbox.addEventListener('change', function () {
            if (this.checked) {
                tanggalSelesaiContainer.style.display = 'block';
                tanggalSelesaiInput.setAttribute('required', 'required');
            } else {
                tanggalSelesaiContainer.style.display = 'none';
                tanggalSelesaiInput.removeAttribute('required');
            }
        });
    </script>
</body>
</html>
