function refreshChoiceList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", "https://wws7d33hy9.execute-api.us-east-2.amazonaws.com/beta/admin/getAllChoices", true);
   xhr.send();
   
   //console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      //console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
    }
  };
}

function processListResponse(result) {
  //console.log("res:" + result);

  var js = JSON.parse(result);
  var choiceList = document.getElementById('spencer');
  var blankChoice = document.getElementById('choice_'); 
  const adminTable = document.getElementById('adminTable')

  

  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var choiceJson = js.list[i];
    console.log(choiceJson);  
	const tableBody = document.getElementById("adminBody");

	/*var idChoice = choiceJson["idChoice"];
	var description = choiceJson["description"];
    var dateCreate = choiceJson["dateCreate"];
    var dateComplete = choiceJson["dateComplete"];*/
   
	let update = tableBody.insertRow(-1);
	console.log("Here");


 	let idChoice = update.insertCell(0);
  	let description = update.insertCell(1);
  	let dateCreate = update.insertCell(2);
  	let dateComplete = update.insertCell(3); 
	
	console.log(choiceJson["idChoice"]);

	idChoice.innerHTML = choiceJson["idChoice"];
	description.innerHTML = choiceJson["description"];
    dateCreate.innerHTML = choiceJson["dateCreate"];
    dateComplete.innerHTML = choiceJson["dateComplete"];

	adminTable.appendChild(update);
	/*var newChoice = blankChoice.cloneNode(true);
	newChoice.setAttribute("id","choice_" + i);
	


	var c_body = newChoice.querySelector("#c_body");

	c_body.querySelector('#c_id').innerHTML = idChoice;	
	c_body.querySelector('#c_description').innerHTML = description;	
	c_body.querySelector('#c_dateCreate').innerHTML = dateCreate;
	c_body.querySelector('#c_dateComplete').innerHTML = dateComplete;
	newChoice.style.display = "block";
	
	choiceList.appendChild(newChoice);*/
  }
}

window.onload = function() {
	//document.getElementById( "choice_" ).style.display = "none";
	refreshChoiceList();
}
