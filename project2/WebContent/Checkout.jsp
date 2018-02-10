<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Checkout</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/LoginRevised.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<div class="header-nightsky">
        <nav class="navbar navbar-default">
            <div class="container">
                <a class="navbar-brand" href="./Main.jsp">FabFlix</a>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="./Search.jsp">Search</a></li>
                    		<li><a href="./Browse.jsp">Browse</a></li>
                        <li><a href="./ShoppingCart.jsp">Checkout</a></li>
                        
                       
                        
                    </ul>
                </div>
            </div>
        </nav>
		<div class="hero">
			<h1>Checkout Information</h1>
			<form id="login_form" method="post" action="#">
			    <input name= "fn" type="text" class="form-control" placeholder="First Name" required="" autofocus="">
				<input name= "ln" type="text" class="form-control" placeholder="Last Name" required="">
				<input name= "cc" type="text" class="form-control" placeholder="Credit Card Number" required="">
				<input name= "ed" type="date" class="form-control" placeholder="Expiration Date" required="">
			    <button class="btn btn-lg btn-primary btn-block" type="submit" value="Login">
					Enter
				</button>
			</form>
		</div>
		<div id="login_error_message"></div>
	</div>
	
	
	
	<!-- include jQuery-->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- include our own JS file -->
	<script src="./Checkout.js"></script>
</body>
</html>