package com.julia.iteration_1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.iteration_1.http.ApproveRequest;
import com.julia.iteration_1.http.ApproveResponse;

public class ApproveAlternativeLambda implements RequestHandler<ApproveRequest, ApproveResponse>{
	LambdaLogger logger;

	
	@Override 
	public ApproveResponse handleRequest(ApproveRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		ApproveResponse response = null;
//		try {
//			String uuidChoice = createChoice(req.description, req.alternatives, req.maxParticipants);
//			if ( uuidChoice != null) {
//				response = new ApproveRequest(uuidChoice);
//			} else {
//				response = new ApproveRequest("Unable to create choice", 400);
//			}			
//		} catch (Exception e) {
//			response = new ApproveRequest("Unable to create choice", 400);
//		}

		return response;
	}
}
