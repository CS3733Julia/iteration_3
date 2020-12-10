package com.julia;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.julia.db.JuliaDAO;
import com.google.gson.Gson;
import com.julia.http.CreateChoiceRequest;
import com.julia.http.CreateChoiceResponse;
import com.julia.http.ParticipateInChoiceRequest;
import com.julia.http.ParticipateInChoiceResponse;
import com.julia.http.completeChoiceRequest;
import com.julia.http.completeChoiceResponse;
import com.julia.model.Alternative;

public class completeChoiceLambdaTest extends LambdaTest{
    void testSuccessInput(String incoming) throws IOException {
    	completeChoiceLambda handler = new completeChoiceLambda();
    	completeChoiceRequest req = new Gson().fromJson(incoming, completeChoiceRequest.class);
       
    	completeChoiceResponse resp = handler.handleRequest(req, createContext("complete"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	completeChoiceLambda handler = new completeChoiceLambda();
    	completeChoiceRequest req = new Gson().fromJson(incoming, completeChoiceRequest.class);

    	completeChoiceResponse resp = handler.handleRequest(req, createContext("complete"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }    
    
	
    
	@Test
    public void testShouldBeOk() {
		String idChoice = createChoice();
		ParticipateInChoiceResponse pr = participateInChoice(idChoice);
		
    	completeChoiceRequest cc = new completeChoiceRequest(pr.choice.alternatives.get(0).idAlternative);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(cc);  
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	
    @Test
    public void testShouldFail() { 	
    	String id = "this is a bad id";
		
    	completeChoiceRequest lr = new completeChoiceRequest(id);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(lr);  
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
    

