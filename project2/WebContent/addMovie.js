
function handleMovieResult(resultDataString) {
	resultDataJson = JSON.parse(resultDataString);
	
	console.log("handle login response");
	console.log(resultDataJson);
	
	alert(resultDataJson["messageA"]);
	alert(resultDataJson["messageB"]);
	alert(resultDataJson["messageC"]);
	$("#add_movie_form")[0].reset();
	
}


function movieForm(formSubmitEvent) {
	console.log("submit login form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	formSubmitEvent.preventDefault();
	jQuery.post(
		"/project2/addMovie", 
		// serialize the login form to the data sent by POST request
		jQuery("#add_movie_form").serialize(),
		(resultDataString) => handleMovieResult(resultDataString));

}

// bind the submit action of the form to a handler function
jQuery("#add_movie_form").submit((event) => movieForm(event));