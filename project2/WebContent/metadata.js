function display() {
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	//formSubmitEvent.preventDefault();
	jQuery.get(
		"/project2/Metadata", 
		// serialize the login form to the data sent by POST request
		jQuery("#getURL").serialize(),
		(resultDataString) => displayMeta(resultDataString));
}

function displayMeta(resultDataString){
	var jsonData = JSON.parse(resultDataString);
	var tableBodyElement = jQuery("#table_body");
	console.log(jsonData);
	for (i in jsonData){
		var table = jsonData[i]["tables"];
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th>" + table + "</th>";
		
		rowHTML += "<th>";
		for (j in jsonData[i]["attributes"]){
			console.log(jsonData[i]["attributes"][j]);
			rowHTML += "<p>" + jsonData[i]["attributes"][j]["field"] + ": " + jsonData[i]["attributes"][j]["type"]+ "</p>";
		}
		rowHTML += "</th>";
		rowHTML += "</tr>";
		tableBodyElement.append(rowHTML);
	}
	
}
//	for (j in jsonData){
//		console.log(jsonData[j]);
//	}
//	for (var i = 0; i < jsonData.length; i++) {
		//alert(jsonData[i][])
		//console.log(jsonData[1]["customers"][0]);
//		for (var j = 0; j < jsonData[j].length; j++){
//			console.log(jsonData[i]);
//		}
//		var rowHTML = "";
//		rowHTML += "<tr>";
//		rowHTML += "<th>" + jsonData[i]["name"] + "</th>";
//		rowHTML += "<th>" + jsonData[i]["year"] + "</th>";
//		
//		rowHTML += "<th>";
//		var star_array = jsonData[i]["title"].split(",");
//		var mID_array = jsonData[i]["mID"].split(",");
//		for (var j = 0; j < star_array.length; j++){
//			rowHTML += "<a href=\"#\" onclick=\"movieClick('"+mID_array[j]+"')\">" + star_array[j] + "</a>";
//			if ((j + 1) == star_array.length){
//				rowHTML += "</a>";
//			}else{
//				rowHTML += ", </a>";
//			}
//		}
//		rowHTML += "</th>";
//		rowHTML += "</tr>";
//		
//		starTableBodyElement.append(rowHTML);
//	}


function movieClick(n){
	alert("hello");
//	window.location.replace("/project2/SingleMovie.jsp?id="+n);
}