<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/css/demo.css">
</head>
<body>
    <h1>đây là file jsp nè</h1>
    <h2>
        ${loc}
    </h2>
    <p>
        ${sauSub.id}
        <br>
        ${sauSub.email}
        <br>
        ${sauSub.fullName}
        <br>
        ${sauSub.address}
        <br>
        ${sauuSub.phone}
    </p>

    <button class="btn btn-success">Button</button>
    <button>button2</button>

</body>
</html>