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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.devbridge-autocomplete/1.4.7/jquery.autocomplete.min.js"></script>
    
	<title>Main</title>
</head>
<body>

    <div class="header-nightsky">
        <nav class="navbar navbar-default">
            <div class="container">
                <a class="navbar-brand" href="./Main.jsp">FabFlix</a>
            		<div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                        		<input id = "autocomplete" name= "search" type="text" class="form-control" placeholder="Normal Search" autofocus="">
                        </li>
                        <li><a href="./ShoppingCart.jsp">Checkout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="hero">
            <h1>Welcome</h1>
            <p>Choose a selection</p>
            <button id = "search" onclick = "searchFunction()" class="btn btn-primary">Search</button>
            <button id = "browse" onclick = "browseFunction()" class="btn btn-primary">Browse</button>
        </div>
    </div>
    
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="./Main.js"></script>
    

</body>
</html>