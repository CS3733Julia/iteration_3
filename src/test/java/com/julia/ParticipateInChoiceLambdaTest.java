package com.julia;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.julia.ParticipateInChoiceLambda;
import com.julia.db.JuliaDAO;
import com.julia.http.CreateChoiceRequest;
import com.julia.http.CreateChoiceResponse;
import com.julia.http.DeleteChoiceRequest;
import com.julia.http.DeleteChoiceResponse;
import com.julia.http.ParticipateInChoiceRequest;
import com.julia.http.ParticipateInChoiceResponse;
import com.julia.model.Member;

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
    	String id = "3283e6ba-e5cd-4e76-bdce-77dd2b3dffb4";
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
       	String id = "a0e2a30f-3bca-4f80-9ab8-6958d887e9ee";
    	String username = "Bill";
    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(id, username, password);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(lr);  
        String idChoice = "";
        try {
        	ParticipateInChoiceLambda handler = new ParticipateInChoiceLambda();
        	ParticipateInChoiceRequest req = new Gson().fromJson(SAMPLE_INPUT_STRING, ParticipateInChoiceRequest.class);
        	
        	ParticipateInChoiceResponse resp = handler.handleRequest(req, createContext("login"));
        	
        	JuliaDAO dao = new JuliaDAO();
    		Member member = dao.getMember(req.idChoice, req.username, req.password);
    		
            Assert.assertEquals(200, resp.statusCode);
            Assert.assertEquals(member.username, username);        
        } catch (Exception e) {
        	Assert.fail("Invalid:" + e.getMessage());
        }
    }
    
    @Test
    public void testPasswordPresent() {
       	String id = "086dcf1c-9d16-41da-a7a6-c5b84391f4fb";
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
    		Member member = dao.getMember(req.idChoice, req.username, req.password);
    		
            Assert.assertEquals(200, resp.statusCode);
            Assert.assertEquals(member.username, username);        
        } catch (Exception e) {
        	Assert.fail("Invalid:" + e.getMessage());
        }
    }
    
    @Test
    public void testTooManyMembers() {
       	String id = "b916141c-f289-45a1-9c77-5a0a284cbe1a";
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
