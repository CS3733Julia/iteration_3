package com.julia.iteration_1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.iteration_1.db.JuliaDAO;
import com.julia.iteration_1.http.DeleteChoiceRequest;
import com.julia.iteration_1.http.DeleteChoiceResponse;


public class DeleteChoiceLambda implements RequestHandler<DeleteChoiceRequest, DeleteChoiceResponse>{
	public LambdaLogger logger = null;

	@Override
	public DeleteChoiceResponse handleRequest(DeleteChoiceRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteChoiceResponse response = null;
		logger.log(req.toString());

		JuliaDAO dao = new JuliaDAO();

		try {
			if (dao.deleteChoice(req.idChoice)) {
				response = new DeleteChoiceResponse(req.idChoice, 200);
			} else {
				response = new DeleteChoiceResponse(req.idChoice, 400, "Unable to delete constant.");
			}
		} catch (Exception e) {
			response = new DeleteChoiceResponse(req.idChoice, 400, "Unable to delete constant: " + req.idChoice + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
