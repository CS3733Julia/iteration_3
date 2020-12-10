package com.julia;

import java.util.ArrayList;

import org.junit.Assert;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.julia.http.CreateChoiceRequest;
import com.julia.http.CreateChoiceResponse;
import com.julia.http.ParticipateInChoiceRequest;
import com.julia.http.ParticipateInChoiceResponse;
import com.julia.http.completeChoiceRequest;
import com.julia.http.completeChoiceResponse;

public class LambdaTest {
	
	/**
	 * Helper method that creates a context that supports logging so you can test lambda functions
	 * in JUnit without worrying about the logger anymore.
	 * 
	 * @param apiCall      An arbitrary string to identify which API is being called.
	 * @return
	 */
	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
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
    
    void completeChoice(String idAlternative) {
    	
    	completeChoiceRequest lr = new completeChoiceRequest(idAlternative);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(lr); 
    	completeChoiceLambda handler = new completeChoiceLambda();
    	completeChoiceRequest req = new Gson().fromJson(SAMPLE_INPUT_STRING, completeChoiceRequest.class);
       
    	completeChoiceResponse resp = handler.handleRequest(req, createContext("complete"));
    }

}
