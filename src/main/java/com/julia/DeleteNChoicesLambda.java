package com.julia;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.db.JuliaDAO;
import com.julia.http.DeleteNChoicesRequest;
import com.julia.http.DeleteNChoicesResponse;
import com.julia.model.Choice;


public class DeleteNChoicesLambda implements RequestHandler<DeleteNChoicesRequest, DeleteNChoicesResponse>{
LambdaLogger logger;
	
	List<Choice> getChoices(int days) throws Exception {
		logger.log("in getConstants");
		JuliaDAO dao = new JuliaDAO();
		
		return dao.deleteChoices(days);
	}

	@Override
	public DeleteNChoicesResponse handleRequest(DeleteNChoicesRequest input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all constants");

		DeleteNChoicesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Choice> list = getChoices(input.days);
			list.clear();
			response = new DeleteNChoicesResponse(list, 200);
		} catch (Exception e) {
			response = new DeleteNChoicesResponse(403, e.getMessage());
		}
		
		return response;
	}
}
