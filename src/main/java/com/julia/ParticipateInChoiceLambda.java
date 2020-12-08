package com.julia;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julia.db.JuliaDAO;
import com.julia.http.ParticipateInChoiceRequest;
import com.julia.http.ParticipateInChoiceResponse;
import com.julia.model.Choice;
import com.julia.model.Member;

public class ParticipateInChoiceLambda implements RequestHandler<ParticipateInChoiceRequest, ParticipateInChoiceResponse>{
	public LambdaLogger logger;
	
	Choice ParticipateInChoice(String idChoice, String username, String password) throws Exception{
		if (logger != null) { logger.log("in getChoice"); }
		
		JuliaDAO dao = new JuliaDAO();
		
		try {
			if(dao.checkAvailability(idChoice, username, password)) {
				return dao.getChoice(idChoice);
			}
			else {
				throw new Exception("To many members signed up for choice"); 
			}
		}
		catch(Exception e) {
			throw new Exception(e.getMessage()); 

		}
	}
	
	String getMemberId(ParticipateInChoiceRequest req) throws Exception {
		try {
			JuliaDAO dao = new JuliaDAO();
			Member member = dao.getMember(req.idChoice, req.username, req.password);
			return member.idMember;
		}
		catch (Exception e) {
			if (logger != null) { logger.log("unable to get member: " + e.getMessage()); }
			return null;
		}
	}
	
	@Override 
	public ParticipateInChoiceResponse handleRequest(ParticipateInChoiceRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		
		ParticipateInChoiceResponse response = null;
		
		try {
			Choice choice = ParticipateInChoice(req.idChoice, req.username, req.password);
			
			String idMember = getMemberId(req);
			response = new ParticipateInChoiceResponse(choice, idMember, 200);
		} catch (Exception e) {
			response = new ParticipateInChoiceResponse(400,  e.getMessage());
		}
		return response;
	}
}
