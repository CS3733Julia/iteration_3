package com.julia.iteration_1;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.iteration_1.db.JuliaDAO;
import com.julia.iteration_1.http.CreateChoiceRequest;
import com.julia.iteration_1.http.CreateChoiceResponse;
import com.julia.iteration_1.model.Alternative;
import com.julia.iteration_1.model.Choice;



public class CreateChoiceLambda implements RequestHandler<CreateChoiceRequest,CreateChoiceResponse>{
	LambdaLogger logger;

	String createChoice(String description, List<String> alternatives, int maxParticipant) throws Exception { 
		if (logger != null) { logger.log("in createChoice"); }
		if(alternatives.size() < 2 || alternatives.size() > 5) { return null;}
		
		JuliaDAO dao = new JuliaDAO();
		List<Alternative> altList = new ArrayList<Alternative>();
		for (String s: alternatives) {
			altList.add(new Alternative(s));
		}
		
		Choice choice = new Choice(description,altList, maxParticipant);
		if(dao.addChoice(choice)) {
			return choice.idChoice;
		}
		return null;
	}
	
	
	@Override 
	public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context)  {

		
		CreateChoiceResponse response;
		try {
			logger = context.getLogger();
			logger.log(req.toString());
			String uuidChoice = createChoice(req.description, req.alternatives, req.maxParticipants);
			if ( uuidChoice != null) {
				response = new CreateChoiceResponse(uuidChoice, 200);
			} else {
				response = new CreateChoiceResponse("", 400, "Unable to create Choice");
			}			
		} catch (Exception e) {
			response = new CreateChoiceResponse("", 400, "(" + e.getMessage() + ")");
		}

		return response;
	}
	
}
