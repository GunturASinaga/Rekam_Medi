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
    <title>Register</title>
</head>

<body>

    @php
    if($message == "error"){
        echo "<p>error boy</p>";
    }
    @endphp


    <div class="position-absolute top-50 start-50 translate-middle">
        <div class="form-floating border border-dark p-4 rounded-4">
            <h1>Rekam Medis</h1>
            <form method="post" action="/registerSubmit">
                @csrf

                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="username" name="username" required>
                    <label for="username">Username</label>
                </div>

                <div class="form-floating my-3">
                    <input type="date" class="form-control" id="birth_date" name="birth_date" required>
                    <label for="birth_date">Birth Date</label>
                </div>

                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="alamat" name="alamat" required>
                    <label for="alamat">Address</label>
                </div>

                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="kontak" name="kontak" required>
                    <label for="kontak">Contact</label>
                </div>

                <div class="form-floating my-3">
                    <input type="password" class="form-control" id="password" name="password" required>
                    <label for="password">Password</label>
                </div>

                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary">Register</button>
                    <a href="/login" class="btn btn-primary">Login</a>
                </div>
            </form>
        </div>

    </div>
</body>

</html>
