<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/LoginRevised.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
	<title>Dashboard</title>
</head>
<body>
	<div class="header-nightsky">
        <nav class="navbar navbar-default">
            <div class="container">
                <a class="navbar-brand" href="./dashboard.jsp">FabFlix</a>
            
            </div>
        </nav>
        <div class="hero">
            <h1>Welcome</h1>
            <p>Choose a selection</p>
            <button id = "insert" onclick = "insertFunction()" class="btn btn-primary">Insert Star</button>
            <button id = "metadata" onclick = "metadataFunction()" class="btn btn-primary">Metadata</button>
            <button id = "movie" onclick = "movieFunction()" class="btn btn-primary">Add Movie</button>
        </div>
    </div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="./dashboard.js"></script>

</body>
</html>