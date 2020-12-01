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
    	String idAlternative = "0336a1f5-5d8d-4c22-a276-fca06f556d07";
    	String idMember = "3225b467-f556-4737-af41-9df2f4967d52";
		
		ApproveRequest ar = new ApproveRequest(idAlternative, idMember);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(ar);  
        String idChoice = "";
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	
	@Test
    public void testAlreadyApproved() {
    	String idAlternative = "8cd71b5c-f1e8-4ca2-ad04-2f53537b377f";
    	String idMember = "4edd3a0c-0ea4-439c-8059-d8c830221078";
		
		ApproveRequest ar = new ApproveRequest(idAlternative, idMember);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(ar);  
        String idChoice = "";
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
