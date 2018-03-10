function searchFunction(){
	var x = document.getElementById("search");
	window.location.replace("/project2/Search.jsp");
}

function browseFunction(){
	var x = document.getElementById("browse");
	window.location.replace("/project2/Browse.jsp");
}

function handleNormalSearch(query) {
	console.log("doing normal search with query: " + query);
	window.location.replace("/project2/MovieListNormalSearch.jsp?id=" + query);
	// TODO: you should do normal search here
}


/*
 * This function is called by the library when it needs to lookup a query.
 * 
 * The parameter query is the query string.
 * The doneCallback is a callback function provided by the library, after you get the
 *   suggestion list from AJAX, you need to call this function to let the library know.
 */

var cache = {};

function handleLookup(query, doneCallback) {
	console.log("autocomplete initiated")
	
	// TODO: if you want to check past query results first, you can do it here

	if (query.trim() in cache){
		console.log("query already in cache");
		console.log(cache);
		handleLookupAjaxSuccess(cache[query.trim()], query, doneCallback) 
	}
	// sending the HTTP GET request to the Java Servlet endpoint hero-suggestion
	// with the query data
	else{
		console.log("sending AJAX request to backend Java Servlet")
		jQuery.ajax({
			"method": "GET",
			// generate the request url from the query.
			// escape the query string to avoid errors caused by special characters 
			"url": "movieStarSuggestion?query=" + escape(query),
			"success": function(data) {
				// pass the data, query, and doneCallback function into the success handler
				console.log("look up ajax successful");
				handleLookupAjaxSuccess(data, query, doneCallback) 
			},
			"error": function(errorData) {
				console.log("lookup ajax error")
				console.log(errorData)
			}
		})
	}
}


/*
 * This function is used to handle the ajax success callback function.
 * It is called by our own code upon the success of the AJAX request
 * 
 * data is the JSON data string you get from your Java Servlet
 * 
 */
function handleLookupAjaxSuccess(data, query, doneCallback) {
	if (!(query.trim() in cache)){
		cache[query.trim()] = data;
	}
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


// bind pressing enter key to a hanlder function
$('#autocomplete').keypress(function(event) {
	// keyCode 13 is the enter key
	if (event.keyCode == 13) {
		// pass the value of the input box to the hanlder function
		handleNormalSearch($('#autocomplete').val())
	}
})

