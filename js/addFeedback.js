/**
 * add feedback to alternative
 */
function processFeedback(result, a_key) {
	console.log(result)
    if(result != "Choice is complete") { 
        alt_array = result["alternatives"]
        for (var i = 0; i < alt_array.length; i++) {
            document.getElementById("a" + (i + 1) + "_errorCode").innerHTML= ''
            document.getElementById("a" + (i + 1) + "_errorCode").style.display = 'none'; 
            document.getElementById("a" + (i + 1) + "_feedback").innerHTML = ""
            for (var j = 0; j < alt_array[i].feedback.length; j++){
                var feedbackString = alt_array[i].feedback[j].description + "<br />" + " -" + alt_array[i].feedback[j].idMember + " @" + alt_array[i].feedback[j].date;
                document.getElementById("a" + (i + 1) + "_feedback").innerHTML += feedbackString + "<br />" + "_______________________________________________________________________________" + "<br />"
            }
        }
    }else{
        document.getElementById(a_key + "_errorCode").innerHTML= 'Choice Has Been Completed, Please Refresh Page'
        document.getElementById(a_key+ "_errorCode").style.display = 'block';  
    }
}

function handleFeedbackClick(e, a_key) {
	console.log("got to feedback")
    var feedback = {};

	feedback["idChoice"] =document.getElementById("raw_choice_id").innerHTML;
    feedback["idAlternative"] = document.getElementById(a_key + '_id').innerHTML;
	feedback["idMember"] =document.getElementById("member_id").innerHTML;
    feedback["description"] = document.getElementById(a_key + "_feedbackDescription").value;

    if(feedback['description'] != ''){
        document.getElementById(a_key + "_errorCode").innerHTML= ''
        document.getElementById(a_key+ "_errorCode").style.display = 'none'; 
        document.getElementById(a_key + "_feedbackDescription").value= ''
        var js1 = JSON.stringify(feedback);
        console.log(js1);
        var xhr1 = new XMLHttpRequest();
        console.log("after printing js");
        xhr1.open("POST", addFeedbackUrl, true);
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
                        processFeedback(js["error"], a_key);
                    }
                    else{
                        processFeedback(js["choice"], a_key);
                    }
                } else {
                    console.log("actual:" + xhr1.responseText)
                    var js = JSON.parse(xhr1.responseText);
                    var err = js["response"];
                    alert(err);
                }
            } else {
                processFeedback("N/A");
            }
        }
    }
    else{
        document.getElementById(a_key + "_errorCode").innerHTML= 'Please Enter Feedback To Add'
        document.getElementById(a_key+ "_errorCode").style.display = 'block';     
    }
}
