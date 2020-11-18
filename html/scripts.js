
var createIsShowing = false
var participateIsShowing = false
var numAlternatives = 2;

const participate = function() {
}

const create = function() {
}

const showParticipateForm = function() {
  if (participateIsShowing) {
    document.getElementById( "participateForm" ).style.display = "none"
  }
  else {
    document.getElementById( "participateForm" ).style.display = "block"
    document.getElementById( "createForm" ).style.display = "none"
    createIsShowing = false;
  }
  participateIsShowing = !participateIsShowing
}

const showCreateForm = function() {
  if (createIsShowing) {
    document.getElementById( "createForm" ).style.display = "none"
  }
  else {
    document.getElementById( "participateForm" ).style.display = "none"
    document.getElementById( "createForm" ).style.display = "block"
    participateIsShowing = false;
  }
  createIsShowing = !createIsShowing;
}

const additionalAlternative = function() {
  if (numAlternatives < 5 ) {
    numAlternatives++
    var element = "alternative_" + numAlternatives
    document.getElementById(element).style.display = "block"
  }
}

window.onload = function() {
  document.getElementById( "participateForm" ).style.display = "none"
  document.getElementById( "createForm" ).style.display = "none"
  document.getElementById( "alternative_3" ).style.display = "none"
  document.getElementById( "alternative_4" ).style.display = "none"
  document.getElementById( "alternative_5" ).style.display = "none"
  
  const showParticipateButton = document.getElementById( 'showParticipateButton' )
  showParticipateButton.onclick = showParticipateForm
  const showCreateButton = document.getElementById( 'showCreateButton' )
  showCreateButton.onclick = showCreateForm
  const additionalAlternativeButton = document.getElementById( 'additionalAlternative' )
  additionalAlternativeButton.onclick = additionalAlternative

}

