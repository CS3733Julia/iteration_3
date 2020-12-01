package com.julia;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.julia.CreateChoiceLambda;
import com.julia.DeleteChoiceLambda;
import com.julia.http.CreateChoiceRequest;
import com.julia.http.CreateChoiceResponse;
import com.julia.http.DeleteChoiceRequest;
import com.julia.http.DeleteChoiceResponse;



public class CreateChoiceLambdaTest extends LambdaTest{
    String testSuccessInput(String incoming) throws IOException {
    	CreateChoiceLambda handler = new CreateChoiceLambda();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);
       
    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
        return resp.idChoice;
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	CreateChoiceLambda handler = new CreateChoiceLambda();
    	CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    @Test
    public void testShouldBeOk() {
    	String testdesc = "this is a test";
    	ArrayList<String> testalt = new ArrayList<String>();
		testalt.add("tesing1");
		testalt.add("tesing2");
		int maxParticipants = 5;
		
		CreateChoiceRequest cc = new CreateChoiceRequest(testdesc, testalt, maxParticipants);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(cc);  
        String idChoice = "";
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
        
        DeleteChoiceRequest dcr = new DeleteChoiceRequest(idChoice);
        DeleteChoiceResponse d_resp = new DeleteChoiceLambda().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(idChoice, d_resp.idChoice);
    }
    
    @Test
    public void testShouldFail() {
    	String testdesc = "this is a test";
    	ArrayList<String> testalt = new ArrayList<String>();
		testalt.add("tesing1");
		int maxParticipants = 5;
		
		CreateChoiceRequest cc = new CreateChoiceRequest(testdesc, testalt, maxParticipants);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(cc);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
