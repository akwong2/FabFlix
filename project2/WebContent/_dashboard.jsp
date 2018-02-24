<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Employee</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/LoginRevised.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<div class="header-nightsky">
        <nav class="navbar navbar-default">
            <div class="container">
                <a class="navbar-brand" href="./_dashboard.jsp">FabFlix</a>
            </div>
        </nav>
		<div class="hero">
			<h1>Employee Login</h1>
			<form id="login_form" method="post" action="#">
			    <input name= "email" type="text" class="form-control" placeholder="Email address" required="" autofocus="">
				<input name= "password" type="password" class="form-control" placeholder="Password" required="">
				<div align="center" class="g-recaptcha" data-sitekey="6LdcXUcUAAAAANLond8Mg6n1Pi73FmIpZ3DQodP3"></div>
			    <button class="btn btn-lg btn-primary btn-block" type="submit" value="Login">
					Login
				</button>
			</form>
		</div>
		<div id="login_error_message"></div>
	</div>
	
	
	
	<!-- include jQuery-->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- include our own JS file -->
	<script src="./_dashboard.js"></script>

</body>
</html>