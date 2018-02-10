function checkURL(n){
	if (n){
		submitLoginForm(n);
	}
}

function submitLoginForm(n) {
	console.log("submit login form");
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	//formSubmitEvent.preventDefault();
	offset = 0;
	
	jQuery.get(
		"/project2/MovieListBrowse", 
		// serialize the login form to the data sent by POST request
		"id="+n,
		(resultDataString) => handleBrowse(resultDataString, -1));
}

function submitParam(n){
	window.location.replace("/project2/Browse.jsp?id="+n);
}

//bind the submit action of the form to a handler function
//jQuery("#browse_form").submit((event) => submitLoginForm(event));

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



function handleBrowse(resultDataString, n){
	document.getElementById("movie_table_body").innerHTML = "";
	
	console.log(resultDataString);
	RD = resultDataString;
	var jsonData = JSON.parse(resultDataString);
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
	
	var starTableBodyElement = jQuery("#movie_table_body");
	
	for (var i = 0; i < Math.min(limit, jsonData.length - offset); i++) {
		var titleInt = i + parseInt(offset);
		console.log(titleInt);
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
	handleBrowse(RD, title);
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
	handleBrowse(RD, year);
}

var offset = 0;

function previousFunction(){
	offset = parseInt(offset,10) - parseInt(limit,10);
	
	if (offset < 0){
		offset = parseInt(offset,10) + parseInt(limit);
	}
	else{
		if (state == 0){
			handleBrowse(RD, title);
		}
		else if (state == 1){
			handleBrowse(RD, year);
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
			handleBrowse(RD, title);
		}
		else if (state == 1){
			handleBrowse(RD, year);
		}
	}
}

function getLimit(){
	var num = document.getElementById("limit");
	var value = num.options[num.selectedIndex].text;
	limit = value;
	//offset = 0;
	handleBrowse(RD, 0);
}






