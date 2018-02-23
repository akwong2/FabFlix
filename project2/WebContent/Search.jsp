<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/Search.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
	<title>Search</title>
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
        		<h1>Search</h1>
            <form id="login_form" method="get" action="#">
			    <input name= "Title" type="text" class="form-control" placeholder="Title" autofocus="">
				<input name= "Year" type="text" class="form-control" placeholder="Year">
				<input name= "Director" type="text" class="form-control" placeholder="Director">
				<input name= "Name of Star" type="text" class="form-control" placeholder="Name of Star">
			    <button class="btn btn-lg btn-primary btn-block" type="submit" value="Login">
					Search
				</button>
			</form>
			<form>
				<select id="limit" onchange="getLimit()">
					<option value="ten" selected>10</option>
					<option value="twentyfive">25</option>
					<option value="fifty">50</option>
					<option value="hundred">100</option>
				</select>
				<noscript><input type = "submit" value= "Submit"></noscript>
			</form>
			
			<div id = "result">
				<table id=star_table class = "table table-inverse">
					<thead>
						<tr>
							<th>Add</th>
							<th onclick="sortByTitle()" style="cursor:pointer">Title</th>
							<th onclick="sortByYear()" style ="cursor:pointer">Year</th>
							<th>Director</th>
							<th>Name of Stars</th>
							<th>Genre</th>
						</tr>
					</thead>
					<tbody id=star_table_body>
					</tbody>
				</table>
			</div>
			<a onclick="previousFunction()" href="#" class="previous round">&#8249;</a>
			<a onclick="nextFunction()" href="#" class="next round">&#8250;</a>
			
        </div>
    </div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src= "./Search.js"></script>

</body>
</html>