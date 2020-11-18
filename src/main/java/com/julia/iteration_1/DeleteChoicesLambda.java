package com.julia.iteration_1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.iteration_1.db.JuliaDAO;
import com.julia.iteration_1.http.DeleteChoicesRequest;
import com.julia.iteration_1.http.DeleteChoicesResponse;

public class DeleteChoicesLambda implements RequestHandler<DeleteChoicesRequest,DeleteChoicesResponse>{
	LambdaLogger logger;

	@Override
	public DeleteChoicesResponse handleRequest(DeleteChoicesRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		DeleteChoicesResponse response = null;
//		try {
//			if (dao.deleteChoice(req.idChoice)) {
//				response = new DeleteChoicesResponse(req.idChoice, 200);
//			} else {
//				response = new DeleteChoicesResponse(req.idChoice, 400, "Unable to delete constant.");
//			}
//		} catch (Exception e) {
//			response = new DeleteChoicesResponse(req.idChoice, 400, "Unable to delete constant: " + req.idChoice + "(" + e.getMessage() + ")");
//		}

		return response;
	}

}
