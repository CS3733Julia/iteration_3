/**
 * complete a choice
 */
function processChoice(result, a_key) {

    if(result != "Choice is complete") { 
        var member = document.getElementById("member_id").innerHTML;
        console.log("LOOK HERE")
        console.log(result)
        processParticipant(result, member)
    }else{
        document.getElementById(a_key + "_errorCode").innerHTML= 'Choice Has Been Completed, Please Refresh Page'
        document.getElementById(a_key+ "_errorCode").style.display = 'block';  
    }
}

function handleCompleteChoiceClick(e, a_key) {
	console.log("got to completed choice")
    var completeChoice = {};

    completeChoice["idAlternative"] = document.getElementById(a_key + '_id').innerHTML;

    var js1 = JSON.stringify(completeChoice);
    console.log(js1);
    var xhr1 = new XMLHttpRequest();
    console.log("after printing js");
    xhr1.open("POST", completeChoiceUrl, true);
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
                console.log(js)
                if(js["statusCode"] == 400){
                    processChoice(js["error"], a_key);
                }
                else{
                    processChoice(js["choice"], a_key);
                }
            } else {
                console.log("actual:" + xhr1.responseText)
                var js = JSON.parse(xhr1.responseText);
                var err = js["response"];
                alert(err);
            }
        } else {
            processChoice("N/A");
        }
    }
}
