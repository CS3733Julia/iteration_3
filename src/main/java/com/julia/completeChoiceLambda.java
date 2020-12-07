package com.julia;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.db.JuliaDAO;
import com.julia.http.completeChoiceRequest;
import com.julia.http.completeChoiceResponse;
import com.julia.model.Choice;

public class completeChoiceLambda implements RequestHandler<completeChoiceRequest, completeChoiceResponse>{
	LambdaLogger logger;
	
	Choice completeChoice(String idAlternative) throws Exception{
		if (logger != null) { logger.log("in completeChoice"); }
		
		JuliaDAO dao = new JuliaDAO();
		Choice choice = dao.getChoicebyAlternative(idAlternative);
		dao.selectAlternative(idAlternative, choice.idChoice);
		
		return choice;
	}
	
	
	@Override 
	public completeChoiceResponse handleRequest(completeChoiceRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		completeChoiceResponse response = null;
		try {
			Choice choice = completeChoice(req.idAlternative);
			response = new completeChoiceResponse(choice, 200);
			logger.log(response.toString());
			
		} catch (Exception e) {
			response = new completeChoiceResponse("Unable to complete Choice", 400);
		}

		return response;
	}
}