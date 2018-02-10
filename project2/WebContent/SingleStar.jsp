<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Single Star</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/Single.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<body onload="submitId()">

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
        		<h1>Star</h1>
	        <form id="getURL" method="get" action="#"> 
				<input type="hidden" name="id" value="<%=request.getParameter("id")%>">
			</form>
			<div id = "result">
				<table id=star_table class = "table table-inverse">
					<thead>
						<tr>
							<th>Name</th>
							<th>Date of Birth</th>
							<th>Featured Movies</th>
						</tr>
					</thead>
					<tbody id=star_table_body>
					</tbody>
				</table>
			</div>			
        </div>
    </div>
    
    <!-- include jQuery-->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- include our own JS file -->
	<script src="./SingleStar.js"></script>

</body>
</html>