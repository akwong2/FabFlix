function getInfo(){
	
	jQuery.get(
			"/project2/Cart", 
			// serialize the login form to the data sent by POST request
			(resultDataString) => handleCart(resultDataString));
}

function handleCart(resultDataString){
	document.getElementById("cart_table_body").innerHTML = "";
	var cartTableBodyElement = jQuery("#cart_table_body");
	var jsonData = JSON.parse(resultDataString);
	for (var i = 0; i < jsonData.length; i++){
		var rowHTML = "";
		rowHTML += "<tr>";
		
		rowHTML += "<th>";
		rowHTML += "<a class='thbut' href=\"#\" onclick=\"editCart('"+jsonData[i]["title"]+"','add')\">Add Copy </a>";
		rowHTML += "<br><a class='thbut2' href=\"#\" onclick=\"editCart('"+jsonData[i]["title"]+"','sub')\">Remove Copy</a>";
		rowHTML += "<br><a class='thbut2' href=\"#\" onclick=\"editCart('"+jsonData[i]["title"]+"','all')\">Remove All</a>";
		rowHTML += "</th>";
	
		rowHTML += "<th>" + jsonData[i]["title"] + "</th>";
		rowHTML += "<th>" + jsonData[i]["quantity"] + "</th>";

		

		rowHTML += "</tr>";
		console.log(rowHTML);
		cartTableBodyElement.append(rowHTML);
	}
}

function editCart(n,k){
	console.log(n);
	
	jQuery.get(
			"/project2/Cart", 
			// serialize the login form to the data sent by POST request
			"id="+n+"&type="+k,
			(resultDataString) => handleCart(resultDataString));
}

function checkoutFunction(){
	window.location.replace("/project2/Checkout.jsp");
}

