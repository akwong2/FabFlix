function submitId() {
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	//formSubmitEvent.preventDefault();
	jQuery.get(
		"/project2/SingleMovie", 
		// serialize the login form to the data sent by POST request
		jQuery("#getURL").serialize(),
		(resultDataString) => searchMovie(resultDataString));

}

function searchMovie(resultDataString){
	//document.getElementById("movie_table_body").innerHTML = "";
	var jsonData = JSON.parse(resultDataString);
	var movieTableBodyElement = jQuery("#movie_table_body");
	
	for (var i = 0; i < 1; i++) {
		var rowHTML = "";
		rowHTML += "<tr>";
		
		rowHTML += "<th>";
		rowHTML += "<a class='thbut' href=\"#\" onclick=\"addToCart('"+jsonData[i]["title"]+"','add')\">Add</a>";
		rowHTML += "</th>";
		
		rowHTML += "<th>" + jsonData[i]["id"] + "</th>";
		rowHTML += "<th>" + jsonData[i]["title"] + "</th>";
		rowHTML += "<th>" + jsonData[i]["year"] + "</th>";
		rowHTML += "<th>" + jsonData[i]["director"] + "</th>";
		rowHTML += "<th>";
		var star_array = jsonData[i]["starsName"].split(",");
		for (var j = 0; j < star_array.length; j++){
			rowHTML += "<a href=\"#\" onclick=\"starClick('"+star_array[j]+"')\">" + star_array[j] + "</a>";
			if ((j + 1) == star_array.length){
				rowHTML += "</a>";
			}else{
				rowHTML += ", </a>";
			}
		}
		rowHTML += "</th>";
		
		console.log(rowHTML);
		
		rowHTML += "<th>";
		var genre_array = jsonData[i]["genresName"].split(",");
		for (var k = 0; k < genre_array.length; k++){
			rowHTML += "<a href=\"#\" onclick=\"genreClick('"+genre_array[k]+"')\">" + genre_array[k] + "</a>";
			if ((k + 1) == genre_array.length){
				rowHTML += "</a>";
			}else{
				rowHTML += ", </a>";
			}
		}
		rowHTML += "</th>";
		rowHTML += "</tr>";
		
		movieTableBodyElement.append(rowHTML);
	}
}

function addToCart(n, k){
	console.log(n);
	
	jQuery.get(
			"/project2/Cart", 
			// serialize the login form to the data sent by POST request
			"id="+n+"&type="+k,
			(resultDataString) => handleCart(resultDataString));
}

function handleCart(resultDataString){
	alert("Added");
}

function genreClick(n){
	window.location.replace("/project2/Browse.jsp?id="+n);
}

function starClick(n){
	window.location.replace("/project2/SingleStar.jsp?id="+n);
}

