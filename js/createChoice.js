/**
 * Respond to server JSON object.
 *
 */
// test commit

function processCreateResponse(result) {
	//console.log("result: " + result);
	result = JSON.parse(result);
	document.getElementById("choice_id").innerHTML = result["idChoice"];
}

function handleCreateClick(e) {

	const description = document.querySelector( '#choiceDescription' ),
				maxParticipants = document.querySelector( '#numParticipants' ),
				alternative_1 = document.querySelector( '#a1_description' ).value,
				alternative_2 = document.querySelector( '#a2_description' ).value,
				alternative_3 = document.querySelector( '#a3_description' ).value,
				alternative_4 = document.querySelector( '#a4_description' ).value,
				alternative_5 = document.querySelector( '#a5_description' ).value;
	var altFields = [				
		alternative_1, alternative_2, alternative_3, alternative_4, alternative_5
	]
	//console.log(altFields)
	var altArray = []
	for (var i = 0; i < 5; i++) {
		if (!(altFields[i] === "")) {
			altArray.push(altFields[i])
		}
	}


	const	json = { 'description':description.value,
								 'alternatives': altArray,
								 'maxParticipants':maxParticipants.value,
							 },
				body = JSON.stringify( json )

				document.querySelector( '#choiceDescription' ).value = ""
				document.querySelector( '#numParticipants' ).value = ""
				document.querySelector( '#a1_description' ).value = ""    
				document.querySelector( '#a2_description' ).value = ""  
				document.querySelector( '#a3_description' ).value = ""   
				document.querySelector( '#a4_description' ).value = ""   
				document.querySelector( '#a5_description' ).value = ""   

	//console.log(body);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", createChoiceUrl, true);

	xhr.send(body);

	// process the results and update the html
	xhr.onloadend = function() {
		//console.log(xhr);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				processCreateResponse(xhr.responseText);
			} else {
				//console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processCreateResponse("N/A");
		}
	}
}
