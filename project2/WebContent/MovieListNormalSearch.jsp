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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.devbridge-autocomplete/1.4.7/jquery.autocomplete.min.js"></script>
	<title>Normal Search</title>
</head>
<body onload="submitId()">

	<div class="header-nightsky">
        <nav class="navbar navbar-default">
            <div class="container">
                <a class="navbar-brand" href="./Main.jsp">FabFlix</a>
            		<div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav navbar-right">
                    		<li>
                    			<input id = "autocomplete" name= "search" type="text" class="form-control" placeholder="Normal Search" autofocus="">
                    		</li>
                        <li><a href="./Search.jsp">Search</a></li>
                    		<li><a href="./Browse.jsp">Browse</a></li>
                        <li><a href="./ShoppingCart.jsp">Checkout</a></li>
                    </ul>
                </div>               
            </div>
        </nav>
        <div class="hero">
        		<h1>Normal Search</h1>
        		<form id="getURL" method="get" action="#"> 
				<input type="hidden" name="id" value="<%=request.getParameter("id")%>">
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
    
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src= "./MovieListNormalSearch.js"></script>

</body>
</html>