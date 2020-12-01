package com.julia;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.db.JuliaDAO;
import com.julia.http.GetAllChoicesRequest;
import com.julia.http.GetAllChoicesResponse;
import com.julia.model.Choice;

public class GetAllChoicesLambda implements RequestHandler<Object, GetAllChoicesResponse>{
	LambdaLogger logger;
	
	List<Choice> getChoices() throws Exception {
		logger.log("in getConstants");
		JuliaDAO dao = new JuliaDAO();
		
		return dao.getAllChoices();
	}
		
	@Override 
	public GetAllChoicesResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all constants");

		GetAllChoicesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Choice> list = getChoices();
			response = new GetAllChoicesResponse(list, 200);
		} catch (Exception e) {
			response = new GetAllChoicesResponse(403, e.getMessage());
		}
		
		return response;
	}
}
