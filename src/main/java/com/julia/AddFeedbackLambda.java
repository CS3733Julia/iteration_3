package com.julia;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.db.JuliaDAO;
import com.julia.http.AddFeedbackRequest;
import com.julia.http.AddFeedbackResponse;
import com.julia.http.ApproveRequest;
import com.julia.http.ApproveResponse;
import com.julia.model.Alternative;
import com.julia.model.Choice;
import com.julia.model.Feedback;

public class AddFeedbackLambda implements RequestHandler<AddFeedbackRequest, AddFeedbackResponse>{
	LambdaLogger logger;
	
	Choice AddFeedback(String idChoice, String idAlternative, String idMember, String description) throws Exception{
		if (logger != null) { logger.log("in AddFeedback"); }
		
		JuliaDAO dao = new JuliaDAO();
		if(dao.checkChoiceComplete(idChoice)) {
			logger.log("choice was already complete");
			throw new Exception("Choice is complete"); 
		}
		
		Feedback feedback = new Feedback(idChoice, idAlternative, idMember, description);
		Choice choice = null;
		if (dao.addFeedback(feedback)) {
			choice = dao.getChoice(idChoice);
		}
		return choice;
	}

	@Override 
	public AddFeedbackResponse handleRequest(AddFeedbackRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		AddFeedbackResponse response = null;
		try {
			Choice choice = AddFeedback(req.idChoice, req.idAlternative, req.idMember, req.description);
			response = new AddFeedbackResponse(choice, 200);
			logger.log(response.toString());
			
		} catch (Exception e) {
			response = new AddFeedbackResponse(e.getMessage(), 400);
		}

		return response;
	}
}
