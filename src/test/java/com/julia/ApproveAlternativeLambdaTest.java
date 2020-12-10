package com.julia;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.julia.ApproveAlternativeLambda;
import com.julia.http.ApproveRequest;
import com.julia.http.ApproveResponse;
import com.julia.http.CreateChoiceRequest;
import com.julia.http.CreateChoiceResponse;
import com.julia.http.DeleteChoiceRequest;
import com.julia.http.DeleteChoiceResponse;
import com.julia.http.ParticipateInChoiceResponse;

public class ApproveAlternativeLambdaTest extends LambdaTest {
    String testSuccessInput(String incoming) throws IOException {
    	ApproveAlternativeLambda handler = new ApproveAlternativeLambda();
    	ApproveRequest req = new Gson().fromJson(incoming, ApproveRequest.class);
       
    	ApproveResponse resp = handler.handleRequest(req, createContext("approve"));
        Assert.assertEquals(200, resp.statusCode);
        return resp.choice.idChoice;
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	ApproveAlternativeLambda handler = new ApproveAlternativeLambda();
    	ApproveRequest req = new Gson().fromJson(incoming, ApproveRequest.class);

    	ApproveResponse resp = handler.handleRequest(req, createContext("approve"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

	@Test
    public void testShouldBeOk() {
		String idChoice = createChoice();
		ParticipateInChoiceResponse pr = participateInChoice(idChoice);
		
		ApproveRequest ar = new ApproveRequest(pr.choice.alternatives.get(0).idAlternative, pr.idMember);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(ar);  
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	
	@Test
    public void testAlreadyApproved() {
		String idChoice = createChoice();
		ParticipateInChoiceResponse pr = participateInChoice(idChoice);
		
		ApproveRequest ar = new ApproveRequest(pr.choice.alternatives.get(0).idAlternative, pr.idMember);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(ar);  
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	
}
