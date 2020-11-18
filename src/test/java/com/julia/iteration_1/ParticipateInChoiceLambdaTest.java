package com.julia.iteration_1;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.julia.iteration_1.db.JuliaDAO;
import com.julia.iteration_1.http.CreateChoiceRequest;
import com.julia.iteration_1.http.CreateChoiceResponse;
import com.julia.iteration_1.http.DeleteChoiceRequest;
import com.julia.iteration_1.http.DeleteChoiceResponse;
import com.julia.iteration_1.http.ParticipateInChoiceRequest;
import com.julia.iteration_1.http.ParticipateInChoiceResponse;

public class ParticipateInChoiceLambdaTest extends LambdaTest{
    String testSuccessInput(String incoming) throws IOException {
    	ParticipateInChoiceLambda handler = new ParticipateInChoiceLambda();
    	ParticipateInChoiceRequest req = new Gson().fromJson(incoming, ParticipateInChoiceRequest.class);
       
    	ParticipateInChoiceResponse resp = handler.handleRequest(req, createContext("login"));
        Assert.assertEquals(200, resp.statusCode);
        return resp.choice.idChoice;
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	ParticipateInChoiceLambda handler = new ParticipateInChoiceLambda();
    	ParticipateInChoiceRequest req = new Gson().fromJson(incoming, ParticipateInChoiceRequest.class);

    	ParticipateInChoiceResponse resp = handler.handleRequest(req, createContext("login"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

   
    @Test
    public void testShouldBeOk() {
    	String id = "9d5b55ce-7655-40ce-9964-82e5ed80f531";
    	String username = "Jack";
    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(id, username, password);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(lr);  
        String idChoice = "";
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testAlreadyPresent() {
       	String id = "9d5b55ce-7655-40ce-9964-82e5ed80f531";
    	String username = "Jack";
    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(id, username, password);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(lr);  
        String idChoice = "";
        try {
        	ParticipateInChoiceLambda handler = new ParticipateInChoiceLambda();
        	ParticipateInChoiceRequest req = new Gson().fromJson(SAMPLE_INPUT_STRING, ParticipateInChoiceRequest.class);
        	
        	ParticipateInChoiceResponse resp = handler.handleRequest(req, createContext("login"));
        	
        	JuliaDAO dao = new JuliaDAO();
    		String name= dao.getMember(req.idChoice, req.username, req.password);
    		
            Assert.assertEquals(200, resp.statusCode);
            Assert.assertEquals(name, username);        
        } catch (Exception e) {
        	Assert.fail("Invalid:" + e.getMessage());
        }
    }
    
    @Test
    public void testPasswordPresent() {
       	String id = "9d5b55ce-7655-40ce-9964-82e5ed80f531";
    	String username = "Jack";
    	String password = "hey";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(id, username, password);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(lr);  
        String idChoice = "";
        try {
        	ParticipateInChoiceLambda handler = new ParticipateInChoiceLambda();
        	ParticipateInChoiceRequest req = new Gson().fromJson(SAMPLE_INPUT_STRING, ParticipateInChoiceRequest.class);
           
        	ParticipateInChoiceResponse resp = handler.handleRequest(req, createContext("login"));
        	
        	JuliaDAO dao = new JuliaDAO();
    		String name= dao.getMember(req.idChoice, req.username, req.password);
    		
            Assert.assertEquals(200, resp.statusCode);
            Assert.assertEquals(name, username);        
        } catch (Exception e) {
        	Assert.fail("Invalid:" + e.getMessage());
        }
    }
    
    @Test
    public void testTooManyMembers() {
       	String id = "9d5b55ce-7655-40ce-9964-82e5ed80f531";
    	String username = "Larry";
    	String username1 = "Harry";
    	String username2 = "Barry";
    	String username3 = "Karry";

    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(id, username, password);
    	ParticipateInChoiceRequest lr1 = new ParticipateInChoiceRequest(id, username1, password);
    	ParticipateInChoiceRequest lr2 = new ParticipateInChoiceRequest(id, username2, password);
    	ParticipateInChoiceRequest lr3 = new ParticipateInChoiceRequest(id, username3, password);

        String SAMPLE_INPUT_STRING = new Gson().toJson(lr);  
        String SAMPLE_INPUT_STRING1 = new Gson().toJson(lr1);  
        String SAMPLE_INPUT_STRING2 = new Gson().toJson(lr2);  
        String SAMPLE_INPUT_STRING3 = new Gson().toJson(lr3);  

        String idChoice = "";
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING1);
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING2);
        	testFailInput(SAMPLE_INPUT_STRING3, 400);      
        } catch (Exception e) {
        	Assert.fail("Invalid:" + e.getMessage());
        }
    }
    
    
    @Test
    public void testShouldFail() { 	
    	String id = "this is a bad id";
    	String username = "Jack";
    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(id, username, password);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(lr);  
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	

}
