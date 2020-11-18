package com.julia.iteration_1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.iteration_1.http.DisapproveRequest;
import com.julia.iteration_1.http.DisapproveResponse;


public class DisapproveAlternativeLambda implements RequestHandler<DisapproveRequest, DisapproveResponse>{
	LambdaLogger logger;

	@Override 
	public DisapproveResponse handleRequest(DisapproveRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		DisapproveResponse response = null;
//		try {
//			String uuidChoice = createChoice(req.description, req.alternatives, req.maxParticipants);
//			if ( uuidChoice != null) {
//				response = new DisapproveResponse(uuidChoice);
//			} else {
//				response = new DisapproveResponse("Unable to create choice", 400);
//			}			
//		} catch (Exception e) {
//			response = new DisapproveResponse("Unable to create choice", 400);
//		}

		return response;
	}
}
