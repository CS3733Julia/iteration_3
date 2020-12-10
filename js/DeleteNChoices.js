function refreshChoiceList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", "https://wws7d33hy9.execute-api.us-east-2.amazonaws.com/beta/admin/getAllChoices", true);
   xhr.send();
   
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

function handleDeleteClick(e) {
	var days = {};
	days["days"] = parseInt(document.getElementById("n_days").value);

	var js1 = JSON.stringify(days);
	var xhr1 = new XMLHttpRequest();
	xhr1.open("POST", deleteChoicesUrl, true);
	xhr1.setRequestHeader('Content-Type', "application/json");
	
	xhr1.send(js1);

	// process the results and update the html
	// This will process results and update HTML as appropriate. 
  xhr1.onloadend = function () {
    if (xhr1.readyState == XMLHttpRequest.DONE) {
		location.reload();
    } else {
      processListResponse("N/A");
    }
  };
}
