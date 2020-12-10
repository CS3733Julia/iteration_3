package com.julia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.julia.ApproveAlternativeLambda;
import com.julia.http.AddFeedbackRequest;
import com.julia.http.AddFeedbackResponse;
import com.julia.http.ApproveRequest;
import com.julia.http.ApproveResponse;
import com.julia.http.CreateChoiceRequest;
import com.julia.http.CreateChoiceResponse;
import com.julia.http.DeleteChoiceRequest;
import com.julia.http.DeleteChoiceResponse;
import com.julia.http.ParticipateInChoiceRequest;
import com.julia.http.ParticipateInChoiceResponse;
import com.julia.model.Alternative;
import com.julia.model.Choice;

public class AddFeedbackLambdaTest extends LambdaTest {
    String testSuccessInput(String incoming) throws IOException {
    	AddFeedbackLambda handler = new AddFeedbackLambda();
    	AddFeedbackRequest req = new Gson().fromJson(incoming, AddFeedbackRequest.class);
       
    	AddFeedbackResponse resp = handler.handleRequest(req, createContext("feedback"));
        Assert.assertEquals(200, resp.statusCode);
        return resp.choice.idChoice;
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	AddFeedbackLambda handler = new AddFeedbackLambda();
    	AddFeedbackRequest req = new Gson().fromJson(incoming, AddFeedbackRequest.class);

    	AddFeedbackResponse resp = handler.handleRequest(req, createContext("feedback"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }
    
    String createChoice() {
    	String testdesc = "this is a test";
    	ArrayList<String> testalt = new ArrayList<String>();
		testalt.add("tesing1");
		testalt.add("tesing2");
		int maxParticipants = 3;
		
		CreateChoiceRequest cc = new CreateChoiceRequest(testdesc, testalt, maxParticipants);
		String SAMPLE_INPUT_STRING = new Gson().toJson(cc);
		CreateChoiceLambda handler = new CreateChoiceLambda();
    	CreateChoiceRequest req = new Gson().fromJson(SAMPLE_INPUT_STRING, CreateChoiceRequest.class);
       
    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        return resp.idChoice;
    }
    
    ParticipateInChoiceResponse participateInChoice(String idChoice) {
		String username = "liz";
		String password = "Flower123";
		ParticipateInChoiceRequest pc = new ParticipateInChoiceRequest(idChoice, username, password );
		
	
		String SAMPLE_INPUT_STRING = new Gson().toJson(pc);
		ParticipateInChoiceLambda handler = new ParticipateInChoiceLambda();
    	ParticipateInChoiceRequest req = new Gson().fromJson(SAMPLE_INPUT_STRING, ParticipateInChoiceRequest.class);
       
    	ParticipateInChoiceResponse resp = handler.handleRequest(req, createContext("participate"));
        return resp;
    }    

	@Test
    public void testShouldBeOk() {
		String idChoice = createChoice();
		ParticipateInChoiceResponse resp = participateInChoice(idChoice);

    	String idAlternative = resp.choice.alternatives.get(0).idAlternative;
    	String idMember = resp.idMember;
    	String description = "this is feedback for an alternative";
		
		AddFeedbackRequest af = new AddFeedbackRequest(idChoice, idAlternative, idMember, description);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(af);  
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	
	@Test
    public void alreadyComplete() {
		String idChoice = createChoice();
		ParticipateInChoiceResponse resp = participateInChoice(idChoice);

    	String idAlternative = resp.choice.alternatives.get(0).idAlternative;
    	String idMember = resp.idMember;
    	String description = "this is feedback for an alternative";
    	completeChoice(idAlternative);
		AddFeedbackRequest af = new AddFeedbackRequest(idChoice, idAlternative, idMember, description);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(af);  
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	
}
