<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/Browse.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
	<title>Browse</title>
</head>
<body onload="checkURL('<%=request.getParameter("id")%>')">

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
        		<h1>Browse</h1>
        		<div class = "browse">
        		<form id="browse_form" method="get" action="#">
	        		<div class="byTitle">
	        			<p>Browse by Title</p>
	        			<input type="button" name="id" value="A" onclick="submitParam('A')">
	        			<input type="button" name="id" value="B" onclick="submitParam('B')">
	        			<input type="button" name="id" value="C" onclick="submitParam('C')">
	        			<input type="button" name="id" value="D" onclick="submitParam('D')">
	        			<input type="button" name="id" value="E" onclick="submitParam('E')">
	        			<input type="button" name="id" value="F" onclick="submitParam('F')">
	        			<input type="button" name="id" value="G" onclick="submitParam('G')">
	        			<input type="button" name="id" value="H" onclick="submitParam('H')">
	        			<input type="button" name="id" value="I" onclick="submitParam('I')">
	        			<input type="button" name="id" value="J" onclick="submitParam('J')">
	        			<input type="button" name="id" value="K" onclick="submitParam('K')">
	        			<input type="button" name="id" value="L" onclick="submitParam('L')">
	        			<input type="button" name="id" value="M" onclick="submitParam('M')">
	        			<input type="button" name="id" value="N" onclick="submitParam('N')">
	        			<input type="button" name="id" value="O" onclick="submitParam('O')">
	        			<input type="button" name="id" value="P" onclick="submitParam('P')">
	        			<input type="button" name="id" value="Q" onclick="submitParam('Q')">
	        			<input type="button" name="id" value="R" onclick="submitParam('R')">
	        			<input type="button" name="id" value="S" onclick="submitParam('S')">
	        			<input type="button" name="id" value="T" onclick="submitParam('T')">
	        			<input type="button" name="id" value="U" onclick="submitParam('U')">
	        			<input type="button" name="id" value="V" onclick="submitParam('V')">
	        			<input type="button" name="id" value="W" onclick="submitParam('W')">
	        			<input type="button" name="id" value="X" onclick="submitParam('X')">
	        			<input type="button" name="id" value="Y" onclick="submitParam('Y')">
	        			<input type="button" name="id" value="Z" onclick="submitParam('Z')">
	        			<input type="button" name="id" value="0" onclick="submitParam('0')">
	        			<input type="button" name="id" value="1" onclick="submitParam('1')">
	        			<input type="button" name="id" value="2" onclick="submitParam('2')">
	        			<input type="button" name="id" value="3" onclick="submitParam('3')">
	        			<input type="button" name="id" value="4" onclick="submitParam('4')">
	        			<input type="button" name="id" value="5" onclick="submitParam('5')">
	        			<input type="button" name="id" value="6" onclick="submitParam('6')">
	        			<input type="button" name="id" value="7" onclick="submitParam('7')">
	        			<input type="button" name="id" value="8" onclick="submitParam('8')">
	        			<input type="button" name="id" value="9" onclick="submitParam('9')">
	            </div>
	            <div class="byGenre">
	            		<p>Browse by Genre</p>
	            		<input type="button" name="id" value="Action" onclick="submitParam('Action')">
	            		<input type="button" name="id" value="Adult" onclick="submitParam('Adult')">
	            		<input type="button" name="id" value="Adventure" onclick="submitParam('Adventure')">
	            		<input type="button" name="id" value="Animation" onclick="submitParam('Animation')">
	            		<input type="button" name="id" value="Biography" onclick="submitParam('Biography')">
	            		<input type="button" name="id" value="Comedy" onclick="submitParam('Comedy')">
	            		<input type="button" name="id" value="Crime" onclick="submitParam('Crime')">
	            		<input type="button" name="id" value="Documentary" onclick="submitParam('Documentary')">
	            		<input type="button" name="id" value="Drama" onclick="submitParam('Drama')">
	            		<input type="button" name="id" value="Family" onclick="submitParam('Family')">
	            		<input type="button" name="id" value="Fantasy" onclick="submitParam('Fantasy')">
	            		<input type="button" name="id" value="History" onclick="submitParam('History')">
	            		<input type="button" name="id" value="Horror" onclick="submitParam('Horror')">
	            		<input type="button" name="id" value="Music" onclick="submitParam('Music')">
	            		<input type="button" name="id" value="Musical" onclick="submitParam('Musical')">
	            		<input type="button" name="id" value="Mystery" onclick="submitParam('Mystery')">
	            		<input type="button" name="id" value="Reality-TV" onclick="submitParam('Reality-TV')">
	            		<input type="button" name="id" value="Romance" onclick="submitParam('Romance')">
	            		<input type="button" name="id" value="Sci-Fi" onclick="submitParam('Sci-Fi')">
	            		<input type="button" name="id" value="Sport" onclick="submitParam('Sport')">
	            		<input type="button" name="id" value="Thriller" onclick="submitParam('Thriller')">
	            		<input type="button" name="id" value="War" onclick="submitParam('War')">
	            		<input type="button" name="id" value="Western" onclick="submitParam('Western')">
	            </div>
	        </form>
            </div>
            
            
            
            <div class = "result">
            
	            <form>
					<select id="limit" onchange="getLimit()">
						<option value="ten" selected>10</option>
						<option value="twentyfive">25</option>
						<option value="fifty">50</option>
						<option value="hundred">100</option>
					</select>
					<noscript><input type = "submit" value= "Submit"></noscript>
				</form>
            
            
				<table id=movie_table class = "table table-inverse">
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
					<tbody id=movie_table_body>
					</tbody>
				</table>
			</div>
			<a onclick="previousFunction()" href="#" class="previous round">&#8249;</a>
			<a onclick="nextFunction()" href="#" class="next round">&#8250;</a>
			
        </div>
    </div>
    
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
    <script src="./Browse.js"></script>

</body>
</html>