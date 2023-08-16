<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Ini adalah percobaan</h1>
    <ul>
        @foreach ($data as $user)
            <li>
                <strong>ID:</strong> {{ $user['id'] }}<br>
                <strong>Username:</strong> {{ $user['username'] }}<br>
                <strong>Birth Date:</strong> {{ $user['birth_date'] }}<br>
                <strong>Address:</strong> {{ $user['alamat'] }}<br>
                <strong>Contact:</strong> {{ $user['kontak'] }}
                <hr>
            </li>
        @endforeach
    </ul>
</body>
</html>
