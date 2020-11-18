package com.julia.iteration_1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.iteration_1.db.JuliaDAO;
import com.julia.iteration_1.http.ParticipateInChoiceRequest;
import com.julia.iteration_1.http.ParticipateInChoiceResponse;
import com.julia.iteration_1.model.Choice;
import com.julia.iteration_1.model.Member;

public class ParticipateInChoiceLambda implements RequestHandler<ParticipateInChoiceRequest, ParticipateInChoiceResponse>{
	public LambdaLogger logger;
	
	Choice ParticipateInChoice(String idChoice, String username, String password) throws Exception{
		if (logger != null) { logger.log("in getChoice"); }
		
		JuliaDAO dao = new JuliaDAO();
		if(dao.checkAvailability(idChoice, username, password)) {
			return dao.getChoice(idChoice);
		}
		else {
			throw new Exception("To many Members signed up for choice"); 
		}
	}
	
	@Override 
	public ParticipateInChoiceResponse handleRequest(ParticipateInChoiceRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		
		ParticipateInChoiceResponse response = null;
		
		try {
			Choice choice = ParticipateInChoice(req.idChoice, req.username, req.password);
			response = new ParticipateInChoiceResponse(choice, 200);
		} catch (Exception e) {
			response = new ParticipateInChoiceResponse(400, "Unable to get choice: " + req.idChoice + "(" + e.getMessage() + ")");
		}
		return response;
	}
}
