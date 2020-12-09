/**
 * API Gateway entry point
 */

// TO-DO: Add base URL here after deploying API
var base_url = " https://wws7d33hy9.execute-api.us-east-2.amazonaws.com/beta/";

// CHOICE
var participateInChoiceUrl = base_url + "choice/participateInChoice";		// POST
var disapproveAlternativeUrl = base_url + "choice/disapproveAlternative";		// POST
var approveAlternativeUrl = base_url + "choice/approveAlternative";		// POST
var completeChoiceUrl = base_url + "choice";			// POST
var createChoiceUrl = base_url + "choice/createChoice";	// POST
var createChoiceUrl = base_url + "choice/createChoice";	// POST
var addFeedbackUrl = base_url + "choice/addFeedback"
var completeChoiceUrl = base_url + "choice/completeChoice"

// ADMIN
var deleteChoicesUrl = base_url + "admin/deleteChoices";				// POST
var getAllChoiceUrl = base_url + "admin/getAllChoices";				// GET