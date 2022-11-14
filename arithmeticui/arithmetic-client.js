/**
1 What is AJAX? AJAX = Asynchronous JavaScript and XML. 
2 The ajax() method is used to perform an AJAX (asynchronous HTTP) request.
3 The $ sign is nothing but an identifier of jQuery() function which
is better than jQuery because it takes a byte and jQuery takes 6 
bytes with the same functionality
4 The syntax to use ajax() method is 
$.ajax({name:value, name:value, ... })
5 Possible names/values in the table below:
https://www.w3schools.com/jquery/ajax_ajax.asp
Name	Value/Description
async	A Boolean value indicating whether the request should be handled asynchronous or not. Default is true
url	    Specifies the URL to send the request to. Default is the current page
6 jQuery is a JavaScript Library
The jQuery library contains the following features:

HTML/DOM manipulation
CSS manipulation
HTML event methods
Effects and animations
AJAX
Utilities

7 jQuery selectors 
document.querySelector("");
document.querySelectorAll("");
which allow you to select and manipulate HTML element(s).
The Document method querySelector() returns 
the first Element within the document that matches 
the specified selector, or group of selectors. 
If no matches are found, null is returned.
$(document).ready(function(){
  $("button").click(function(){
    $("p").hide();
  });
});
The above code says when the document is ready, select all the 
buttons, and when anyone of the is clicked, hide all "p" elements.
7.1 The element Selector
You can select all <p> elements on a page like this:
$("p")
// With jQuery
$('.item').css('color', '#000');
// Without jQuery
document.querySelector('item').style.color = '#000';

7.2 The #id Selector
To find an element with a specific id, write a hash character, 
followed by the id of the HTML element:
$("#test")
7.3 The .class Selector
The jQuery .class selector finds elements with a specific class.
To find elements with a specific class, write a period character, followed by the name of the class:
$(".test")

 */
var serverURL = "http://localhost:8084";
function updateArithmetic() {
	var operator = document.querySelector('#arithmetic').selectedOptions[0].text;
	var clientData = { operator: operator};
	//Asynchronously execute the request, then ...
	//when I use jQuery() instead of $, there will be an error saying that it is not a function
	$.ajax({
		url: serverURL + '/arithmetic/random?' + $.param(clientData),
		type: 'GET',
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).then(function(data) {
		console.log("arithmetic expression data = " + JSON.stringify(data));
		// Cleans the attempt form
		// select the attempt form by its it, clean its content
		$("#attempt-form").find("input[name='result-attempt']").val("");
		$("#attempt-form").find("input[name='user-alias']").val("");
		// Gets a random challenge from API and loads the data in the HTML
		//find the elements for showing the arithmetic factors by their class name
		//load the data returned from the server to the corresponding elements
		$('.arithmetic-a').empty().append(data.factorA);
		$('.arithmetic-b').empty().append(data.factorB);
	});
	
	//jQuery().ajax({name:value, name:value,...}).then();
}
// When the document is loaded completely, first, update the arithmetic 
// expression
//$.post(URL,data,callback);

$(document).ready(function() {
	updateArithmetic();
	// select the attempt form and define the submit function for the form's action
	$("#attempt-form").submit(function(event) {
		// Don't submit the form normally
		event.preventDefault();
		// Get some values from elements on the page
		var a = $('.arithmetic-a').text();
		var b = $('.arithmetic-b').text();
		var operator = document.querySelector('#arithmetic').selectedOptions[0].text;
		//var operatorValue = document.querySelector('#arithmetic').selectedOptions[0].value;
		$('.debug-message').empty().append("You have chosen " + operator + " calculation");
		// define three variables $form, attempt and userAlias
		var $form = $(this),
			attempt = $form.find("input[name='result-attempt']").val(),
			userAlias = $form.find("input[name='user-alias']").val();
		// Compose the data in the format that the API is expecting
		var data = { user: { alias: userAlias }, arithmetic: { factorA: a, factorB: b, operator: operator}, resultAttempt: attempt };
		// Send the data using post
		$.ajax({
			url: serverURL + '/arithmetic/attempt',
			type: 'POST',
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(result) {
				if (result.correct) {
					//The empty() method removes all child nodes and content from the selected elements.
					$('.result-message').empty().append("The result is correct! Congratulations!");
				} else {
					$('.result-message').empty().append("Oops that's not correct! But keep trying!");
				}
			}
		});
		updateArithmetic();
	});
});
