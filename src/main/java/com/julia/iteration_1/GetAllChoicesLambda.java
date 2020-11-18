package com.julia.iteration_1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.iteration_1.http.GetAllChoicesRequest;
import com.julia.iteration_1.http.GetAllChoicesResponse;

public class GetAllChoicesLambda implements RequestHandler<GetAllChoicesRequest, GetAllChoicesResponse>{
	LambdaLogger logger;
	
	@Override 
	public GetAllChoicesResponse handleRequest(GetAllChoicesRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		GetAllChoicesResponse response = null;
//		try {
//			String uuidChoice = createChoice(req.description, req.alternatives, req.maxParticipants);
//			if ( uuidChoice != null) {
//				response = new GetAllChoicesResponse(uuidChoice);
//			} else {
//				response = new GetAllChoicesResponse("Unable to create choice", 400);
//			}			
//		} catch (Exception e) {
//			response = new CreateChoiceGetAllChoicesResponseResponse("Unable to create choice", 400);
//		}

		return response;
	}
}
