package com.julia;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.db.JuliaDAO;
import com.julia.http.ApproveRequest;
import com.julia.http.ApproveResponse;
import com.julia.model.Alternative;
import com.julia.model.Choice;

public class ApproveAlternativeLambda implements RequestHandler<ApproveRequest, ApproveResponse>{
	LambdaLogger logger;
	
	Choice ApproveAlternative(String idAlternative, String idMember) throws Exception{
		if (logger != null) { logger.log("in ApproveAlternative"); }
	
		JuliaDAO dao = new JuliaDAO();
		
		Choice choice = dao.getChoicebyAlternative(idAlternative);
		if(dao.checkChoiceComplete(choice.idChoice)) {
			throw new Exception("Choice is complete"); 
		}
		
		if (dao.checkApproval(idAlternative, idMember)) {
			dao.deleteApproval(idAlternative, idMember);
		}
		else if (dao.checkDisapproval(idAlternative, idMember)) {
			dao.deleteDisapproval(idAlternative, idMember);
			dao.createApproval(idAlternative, idMember);
		}
		else {
			dao.createApproval(idAlternative, idMember);
		}
		
		choice = dao.getChoicebyAlternative(idAlternative);
		return choice;
	}

	@Override 
	public ApproveResponse handleRequest(ApproveRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		ApproveResponse response = null;
		try {
			Choice choice = ApproveAlternative(req.idAlternative, req.idMember);
			response = new ApproveResponse(choice, 200);
			logger.log(response.toString());
			
		} catch (Exception e) {
			response = new ApproveResponse(e.getMessage(), 400);
		}

		return response;
	}
}
