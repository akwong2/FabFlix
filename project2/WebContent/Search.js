function submitLoginForm(formSubmitEvent) {
	console.log("submit login form");
	console.log(jQuery("#login_form"));
	console.log(jQuery("#login_form").serialize());
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	formSubmitEvent.preventDefault();
	offset = 0;
	jQuery("#login_form").serialize();
	jQuery.get(
		"/project2/MovieListSearch", 
		// serialize the login form to the data sent by POST request
		jQuery("#login_form").serialize(),
		(resultDataString) => handleSearch(resultDataString, -1));

}

// bind the submit action of the form to a handler function
jQuery("#login_form").submit((event) => submitLoginForm(event));

function getAsc(prop) {  
    return function(a, b) {  
        if (a[prop] > b[prop]) {  
            return 1;  
        } else if (a[prop] < b[prop]) {  
            return -1;  
        }  
        return 0;  
    }  
}  

function getDesc(prop) {  
    return function(a, b) {  
        if (a[prop] < b[prop]) {  
            return 1;  
        } else if (a[prop] > b[prop]) {  
            return -1;  
        }  
        return 0;  
    }  
}  

var RD;
var limit = 10;
var length;

function handleSearch(resultData, n) {
	document.getElementById("star_table_body").innerHTML = "";
	RD = resultData;
	var jsonData = JSON.parse(resultData);
	length = jsonData.length;
	if(n == 0){
		jsonData.sort(getAsc("title"));
	}
	if(n == 1){
		jsonData.sort(getDesc("title"));
	}
	if (n == 2){
		jsonData.sort(getAsc("year"));
	}
	if (n == 3){
		jsonData.sort(getDesc("year"));
	}	
	// populate the star table
	var starTableBodyElement = jQuery("#star_table_body");
	
	for (var i = 0; i < Math.min(limit, jsonData.length - offset); i++) {
		var titleInt = i + parseInt(offset);
		var rowHTML = "";
		var id = jsonData[i+offset]["id"];
		rowHTML += "<tr>";
		rowHTML += "<th>";
		rowHTML += "<a class='thbut' href=\"#\" onclick=\"addToCart('"+jsonData[i+offset]["title"]+"','add')\">Add</a>";
		rowHTML += "</th>";
		rowHTML += "<th class='but' id=\""+id+"\" onclick=\"titleClick("+id+")\" style=\"cursor:pointer\">" + jsonData[i+offset]["title"] + "</th>";
		rowHTML += "<th>" + jsonData[i+offset]["year"] + "</th>";
		rowHTML += "<th>" + jsonData[i+offset]["director"] + "</th>";
		rowHTML += "<th>";
		var star_array = jsonData[i+offset]["starsName"].split(",");
		for (var j = 0; j < star_array.length; j++){
			rowHTML += "<a class='thbut' href=\"#\" onclick=\"starClick('"+star_array[j]+"')\">" + star_array[j] + "</a>";
			if ((j + 1) == star_array.length){
				rowHTML += "</a>";
			}else{
				rowHTML += ", </a>";
			}
		}
		rowHTML += "</th>";
		
		rowHTML += "<th>" + jsonData[i+offset]["genresName"] + "</th>";
		rowHTML += "</tr>"
		starTableBodyElement.append(rowHTML);
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
	var jsonData = JSON.parse(resultDataString);
	alert("Added");
}

function starClick(n){
	window.location.replace("/project2/SingleStar.jsp?id="+n);
}

function titleClick(n){
	window.location.replace("/project2/SingleMovie.jsp?id="+n.id);
	//console.log(num.innerHTML);
}

var title = 1;
var year = 3;
var state = 0;

function sortByTitle(){
	offset = 0;
	if (title == 0){
		title = title +1;
	}
	else{
		title = title -1;
	}
	state = 0;
	handleSearch(RD, title);
}

function sortByYear(){
	offset = 0;
	if (year == 2){
		year = year +1;
		
	}
	else{
		year = year -1;
	}
	state = 1;
	handleSearch(RD, year);
}

var offset = 0;

function previousFunction(){
	offset = parseInt(offset,10) - parseInt(limit,10);
	
	if (offset < 0){
		offset = parseInt(offset,10) + parseInt(limit);
	}
	else{
		if (state == 0){
			handleSearch(RD, title);
		}
		else if (state == 1){
			handleSearch(RD, year);
		}
	}
}

function nextFunction(){
	offset = parseInt(offset,10) + parseInt(limit,10);
	
	if (offset > length){
		offset = parseInt(offset) - parseInt(limit);
	}
	else {
		if (state == 0){
			handleSearch(RD, title);
		}
		else if (state == 1){
			handleSearch(RD, year);
		}
	}
}

function getLimit(){
	var num = document.getElementById("limit");
	var value = num.options[num.selectedIndex].text;
	limit = value;
	//offset = 0;
	console.log(offset);
	console.log(limit);
	handleSearch(RD, 0);
}


