package com.julia;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.db.JuliaDAO;
import com.julia.http.ApproveResponse;
import com.julia.http.DisapproveRequest;
import com.julia.http.DisapproveResponse;
import com.julia.model.Choice;


public class DisapproveAlternativeLambda implements RequestHandler<DisapproveRequest, DisapproveResponse>{
	LambdaLogger logger;
	
	Choice DisapproveAlternative(String idAlternative, String idMember) throws Exception{
		if (logger != null) { logger.log("in DisapproveAlternative"); }
		
		JuliaDAO dao = new JuliaDAO();
		if (dao.checkDisapproval(idAlternative, idMember)) {
			dao.deleteDisapproval(idAlternative, idMember);
		}
		else if (dao.checkApproval(idAlternative, idMember)) {
			dao.deleteApproval(idAlternative, idMember);
			dao.createDisapproval(idAlternative, idMember);
		}
		else {
			dao.createDisapproval(idAlternative, idMember);
		}
		
		Choice choice = dao.getChoicebyAlternative(idAlternative);
		return choice;
	}

	@Override 
	public DisapproveResponse handleRequest(DisapproveRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		DisapproveResponse response = null;
		try {
			Choice choice = DisapproveAlternative(req.idAlternative, req.idMember);
			response = new DisapproveResponse(choice, 200);
			logger.log(response.toString());
			
		} catch (Exception e) {
			response = new DisapproveResponse("Unable to disapprove alternative", 400);
		}

		return response;

	}
}
