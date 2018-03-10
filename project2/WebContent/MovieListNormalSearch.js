/*
 * This function is called by the library when it needs to lookup a query.
 * 
 * The parameter query is the query string.
 * The doneCallback is a callback function provided by the library, after you get the
 *   suggestion list from AJAX, you need to call this function to let the library know.
 */
function handleLookup(query, doneCallback) {
	console.log("autocomplete initiated")
	console.log("sending AJAX request to backend Java Servlet")
	
	// TODO: if you want to check past query results first, you can do it here
	
	// sending the HTTP GET request to the Java Servlet endpoint hero-suggestion
	// with the query data
	jQuery.ajax({
		"method": "GET",
		// generate the request url from the query.
		// escape the query string to avoid errors caused by special characters 
		"url": "movieStarSuggestion?query=" + escape(query),
		"success": function(data) {
			// pass the data, query, and doneCallback function into the success handler
			handleLookupAjaxSuccess(data, query, doneCallback) 
		},
		"error": function(errorData) {
			console.log("lookup ajax error")
			console.log(errorData)
		}
	})
}


/*
 * This function is used to handle the ajax success callback function.
 * It is called by our own code upon the success of the AJAX request
 * 
 * data is the JSON data string you get from your Java Servlet
 * 
 */
function handleLookupAjaxSuccess(data, query, doneCallback) {
	console.log("lookup ajax successful")
	
	// parse the string into JSON
	var jsonData = JSON.parse(data);
	console.log(jsonData)
	
	// TODO: if you want to cache the result into a global variable you can do it here

	// call the callback function provided by the autocomplete library
	// add "{suggestions: jsonData}" to satisfy the library response format according to
	//   the "Response Format" section in documentation
	doneCallback( { suggestions: jsonData } );
}


/*
 * This function is the select suggestion hanlder function. 
 * When a suggestion is selected, this function is called by the library.
 * 
 * You can redirect to the page you want using the suggestion data.
 */
function handleSelectSuggestion(suggestion) {
	// TODO: jump to the specific result page based on the selected suggestion
	
	console.log("you select " + suggestion["value"]);
	console.log(suggestion["data"])
	var cat = suggestion["data"]["category"];

	if (cat == "MOVIES")
		window.location.replace("/project2/SingleMovie.jsp?id="+suggestion["data"]["id"]);
	else if (cat == "STARS")
		window.location.replace("/project2/SingleStar.jsp?id="+suggestion["value"]);

}


/*
 * This statement binds the autocomplete library with the input box element and 
 *   sets necessary parameters of the library.
 * 
 * The library documentation can be find here: 
 *   https://github.com/devbridge/jQuery-Autocomplete
 *   https://www.devbridge.com/sourcery/components/jquery-autocomplete/
 * 
 */
// $('#autocomplete') is to find element by the ID "autocomplete"
$('#autocomplete').autocomplete({
	// documentation of the lookup function can be found under the "Custom lookup function" section
    lookup: function (query, doneCallback) {
    		handleLookup(query, doneCallback)
    },
    onSelect: function(suggestion) {
    		handleSelectSuggestion(suggestion)
    },
    // set the groupby name in the response json data field
    groupBy: "category",
    // set delay time
    deferRequestBy: 300,
    // there are some other parameters that you might want to use to satisfy all the requirements
    // TODO: add other parameters, such as mininum characters
    minChars: 3,
    triggerSelectOnValidInput: false
});




// NORMAL FUNCTION
function submitNormalSearchForm(query) {
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
//	offset = 0;
//	console.log(query);
//	jQuery.get(
//		"/project2/MainSuggestion", 
//		// serialize the login form to the data sent by POST request
//		"id="+query,
//		(resultDataString) => handleSearch(resultDataString, -1));
	offset = 0;
	window.location.replace("/project2/MovieListNormalSearch.jsp?id="+query);

}

function submitId() {
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	//formSubmitEvent.preventDefault();
	console.log()
	title = 0;
	jQuery.get(
		"/project2/MainSuggestion", 
		// serialize the login form to the data sent by POST request
		jQuery("#getURL").serialize(),
		(resultDataString) => handleSearch(resultDataString, title));

}


// bind pressing enter key to a hanlder function
$('#autocomplete').keypress(function(event) {
	// keyCode 13 is the enter key
	if (event.keyCode == 13) {
		// pass the value of the input box to the hanlder function
		submitNormalSearchForm($('#autocomplete').val())
	}
})

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
	if (RD != null){
		handleSearch(RD, title);
	}
}

function sortByYear(){
	offset = 0;
	console.log("year: "+year);
	console.log("state: "+state);
	if (year == 2){
		year = year +1;
	}
	else{
		year = year -1;
	}
	state = 1;
	if (RD != null){
		handleSearch(RD, year);
	}
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
	if (state == 0){
		handleSearch(RD, title);
	}
	else if (state == 1){
		handleSearch(RD, year);
	}
//	handleSearch(RD, -1);
}