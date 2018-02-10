
var creditID;

function handleLoginResult(resultDataString) {	
	console.log(resultDataString);
	resultDataJson = JSON.parse(resultDataString);
//	resultDataJson = JSON.parse(JSON.stringify(resultDataString));
	
	console.log("handle login response");
	console.log(resultDataJson);
	console.log(resultDataJson["status"]);

	if (resultDataJson["status"] == "success") {
		creditID = resultDataJson["cID"];
		jQuery.get(
				"/project2/Cart", 
				// serialize the login form to the data sent by POST request
				(resultDataString) => handleSales(resultDataString));
	} else {
		console.log("show error message");
		console.log(resultDataJson["message"]);
		jQuery("#login_error_message").text(resultDataJson["message"]);
		$("#login_form")[0].reset();
	}
}

function handleSales(resultDataString){
	var jsonData = JSON.parse(resultDataString);
	console.log(jsonData);
	for (var i = 0; i < jsonData.length; i++){
		console.log(jsonData[i]["quantity"]);
		for (var j = 0; j < jsonData[i]["quantity"]; j++){
			jQuery.get(
					"/project2/Confirm",
					"title=" +jsonData[i]["title"] + "&quan="+jsonData[i]["quantity"] + "&id=" + creditID,
					(resultDataString) => handleConfirm(resultDataString));
		}
	}
	
	
}

function handleConfirm(resultDataString){
	alert(resultDataString);
	jQuery.get(
			"/project2/Cart", 
			// serialize the login form to the data sent by POST request
			"id=000&type=rmall",
			(resultDataString) => handleMain(resultDataString));
}

function handleMain(resultDataString){
	window.location.replace("/project2/Main.jsp");
}

function submitLoginForm(formSubmitEvent) {
	console.log("submit login form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	formSubmitEvent.preventDefault();

	jQuery.post(
		"/project2/Checkout", 
		// serialize the login form to the data sent by POST request
		jQuery("#login_form").serialize(),
		(resultDataString) => handleLoginResult(resultDataString));

}

// bind the submit action of the form to a handler function
jQuery("#login_form").submit((event) => submitLoginForm(event));