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
    <title>Login</title>
</head>

<body>

    <div class="position-absolute top-50 start-50 translate-middle border border-dark p-4 rounded-4">
        <div class="form-floating">
            <h1 class="text-center">Rekam Medis</h1>
            <form method="post" action="{{ route('login.submit') }}">
                @csrf

                <div class="form-floating my-3">
                    <input type="text" class="form-control" id="username" name="username" required>
                    <label for="username">Username</label>
                </div>

                <div class="form-floating my-3">
                    <input type="password" class="form-control" id="password" name="password" required>
                    <label for="password">Password</label>
                </div>

                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary">Login</button>
                    <a href="/register" class="btn btn-primary">Register</a>
                </div>
            </form>
        </div>
    </div>
</body>

</html>
