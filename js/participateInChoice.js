/**
 * participating in a choice
 */
function processParticipant(result) {
	console.log(result)
	console.log(result.description);
	document.getElementById("display_choiceDescription").innerHTML = result["description"];
	document.getElementById("date_of_creation").innerHTML = "Date of Creation: " + result["dateCreate"];
	document.getElementById("unique_id").innerHTML = "Choice ID: " + result["idChoice"];
	if(result["completedDate"] != "") {
		document.getElementById("date_of_completion").innerHTML = "Date of Completion: " + result["completedDate"];
	}
	alt_array = result["alternatives"]
	console.log(alt_array)
	for (var i = 0; i < alt_array.length; i++) {
		console.log(alt_array[i].descriptionAlternative);
		/*document.getElementById("a" + i).style.display = "block";*/
		console.log("a" + (i + 1) + "_description")
		document.getElementById("a" + (i + 1) + "_descriptiondisplay").innerHTML = alt_array[i].descriptionAlternative;
		
	}
	
}

function handleParticipantClick(e) {
	console.log("got here")
	var form = document.participateForm;
	var participant = {};

	participant["idChoice"] = document.getElementById("choiceId").value;
	participant["username"] = document.getElementById("username").value;
	participant["password"] = document.getElementById("password").value;

	var js1 = JSON.stringify(participant);
	console.log(js1);
	var xhr1 = new XMLHttpRequest();
	console.log("after printing js");
	xhr1.open("POST", participateInChoiceUrl, true);
	console.log("after post");
	
	xhr1.send(js1);
	console.log("after sending js");

	// process the results and update the html
	xhr1.onloadend = function() {
		console.log(xhr1);
		/*console.log(xhr1.request);*/
		if (xhr1.readyState == XMLHttpRequest.DONE) {
			if (xhr1.status == 200) {
				var js = JSON.parse(xhr1.responseText);
				processParticipant(js["choice"]);
			} else {
				console.log("actual:" + xhr1.responseText)
				var js = JSON.parse(xhr1.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processParticipant("N/A");
		}
	}
}
