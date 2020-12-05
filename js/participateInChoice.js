/**
 * participating in a choice
 */
function processParticipant(result, member) {
	console.log(result)
	console.log(member);
	document.getElementById( "display" ).style.display = "block"
	document.getElementById("display_choiceDescription").innerHTML = result["description"];
	document.getElementById("date_of_creation").innerHTML = "Date of Creation: " + result["dateCreate"];
    document.getElementById("unique_id").innerHTML = "Choice ID: " + result["idChoice"];
    document.getElementById("member_id").innerHTML = member;
	if(result["dateComplete"] != "") {
		console.log(result["dateComplete"])
		document.getElementById("date_of_completion").innerHTML = "Date of Completion: " + result["dateComplete"];
	}
	alt_array = result["alternatives"]
    console.log(alt_array)
    for(var i = 0; i < 5; i++){ 
        document.getElementById("alt" + (i + 1) + "_card").style.display = 'none';     
    }

	for (var i = 0; i < alt_array.length; i++) {
		processPreference(result, alt_array[i].idAlternative)
		console.log(alt_array[i].descriptionAlternative);
		console.log("a" + (i + 1) + "_description")
		document.getElementById("a" + (i + 1) + "_descriptiondisplay").innerHTML = alt_array[i].descriptionAlternative;
		document.getElementById("a" + (i + 1) + "_id").innerHTML = alt_array[i].idAlternative;
		document.getElementById("alt" + (i + 1) + "_card").style.display = 'block'; 
		if(result["dateComplete"] != "") {
			if(alt_array[i].isChosen){
				document.getElementById("a" + (i + 1) + "_header").style = "background-color: green";
				document.getElementById("a" + (i + 1) + "_descriptiondisplay").innerHTML += "- Chosen"
			}
			document.getElementById("a" + (i + 1) + "_approveButton").disabled = true;
			document.getElementById("a" + (i + 1) + "_disapproveButton").disabled = true;
			document.getElementById("a" + (i + 1) + "_select_button").disabled = true;
			document.getElementById("a" + (i + 1) + "_addFeedbackButton").disabled = true;
			document.getElementById("a" + (i + 1) + "_feedbackDescription").disabled = true;
		}  
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
				processParticipant(js["choice"], js["idMember"]);
			} else {
				console.log("actual:" + xhr1.responseText)
				var js = JSON.parse(xhr1.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processParticipant("N/A", "N/A");
		}
	}
}
