function submitId() {
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	//formSubmitEvent.preventDefault();
	jQuery.get(
		"/project2/SingleStar", 
		// serialize the login form to the data sent by POST request
		jQuery("#getURL").serialize(),
		(resultDataString) => searchMovie(resultDataString));

}

function searchMovie(resultDataString){
	console.log(resultDataString);
	var jsonData = JSON.parse(resultDataString);
	var starTableBodyElement = jQuery("#star_table_body");
	
	for (var i = 0; i < 1; i++) {
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th>" + jsonData[i]["name"] + "</th>";
		rowHTML += "<th>" + jsonData[i]["year"] + "</th>";
		
		rowHTML += "<th>";
		var star_array = jsonData[i]["title"].split(",");
		var mID_array = jsonData[i]["mID"].split(",");
		for (var j = 0; j < star_array.length; j++){
			rowHTML += "<a href=\"#\" onclick=\"movieClick('"+mID_array[j]+"')\">" + star_array[j] + "</a>";
			if ((j + 1) == star_array.length){
				rowHTML += "</a>";
			}else{
				rowHTML += ", </a>";
			}
		}
		rowHTML += "</th>";
		rowHTML += "</tr>";
		
		starTableBodyElement.append(rowHTML);
	}
}

function movieClick(n){
	window.location.replace("/project2/SingleMovie.jsp?id="+n);
}