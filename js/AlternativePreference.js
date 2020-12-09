/**
 * Select Alternative Preference
 */
function processPreference(result, alt_id) {
	console.log(result)
	if(result != "Choice is complete") {
		alt_array = result["alternatives"]
		for (var i = 0; i < alt_array.length; i++) {
			document.getElementById("a" + (i + 1) + "_errorCode").innerHTML= ''
        	document.getElementById("a" + (i + 1) + "_errorCode").style.display = 'none';  
			if(alt_id == alt_array[i].idAlternative){
				document.getElementById("a" + (i + 1) + "_approvers").innerHTML = ""
				document.getElementById("a" + (i + 1) + "_disapprovers").innerHTML = ""
				for(var j = 0; j <alt_array[i].approved.length; j++){
					document.getElementById("a" + (i + 1) + "_approvers").innerHTML += alt_array[i].approved[j] + "<br />"
				}
				for(var k = 0; k <alt_array[i].disapproved.length; k++){
					document.getElementById("a" + (i + 1) + "_disapprovers").innerHTML += alt_array[i].disapproved[k] + "<br />"
				}
				document.getElementById("a" + (i + 1) + "_numApprove").innerHTML = "Approved: " + alt_array[i].approved.length;
				document.getElementById("a" + (i + 1) + "_numDisapprove").innerHTML = "Disapproved: " + alt_array[i].disapproved.length;
			}
		}
	}else{
		document.getElementById(alt_id + "_errorCode").innerHTML= 'Choice Has Been Completed, Please Refresh Page'
        document.getElementById(alt_id+ "_errorCode").style.display = 'block';  
	}
}

function handleApprovalClick(e, a_id, a_key) {
	console.log("Approving")
    var approval = {};
    
	approval["idAlternative"] = a_id;
	approval["idMember"] =document.getElementById("member_id").innerHTML;

    console.log(approval)

	var js1 = JSON.stringify(approval);
	console.log(js1);
	var xhr1 = new XMLHttpRequest();
	console.log("after printing js");
	xhr1.open("POST", approveAlternativeUrl, true);
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
				if(js["statusCode"] == 400){
					processPreference(js["error"], a_key);
				}
				else{
					processPreference(js["choice"], a_id);
				}
			} else {
				console.log("actual:" + xhr1.responseText)
				var js = JSON.parse(xhr1.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processPreference("N/A", a_id);
		}
	}
}

function handleDisapprovalClick(e, a_id, a_key) {
	console.log("Disapproving")
    var disapproval = {};
    
	disapproval["idAlternative"] = a_id;
	disapproval["idMember"] =document.getElementById("member_id").innerHTML;

    console.log(disapproval)

	var js1 = JSON.stringify(disapproval);
	console.log(js1);
	var xhr1 = new XMLHttpRequest();
	console.log("after printing js");
	xhr1.open("POST", disapproveAlternativeUrl, true);
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
				if(js["statusCode"] == 400){
					processPreference(js["error"], a_key);
				}
				else{
					processPreference(js["choice"], a_id);
				}
			} else {
				console.log("actual:" + xhr1.responseText)
				var js = JSON.parse(xhr1.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processPreference("N/A", a_id);
		}
	}
}