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
	String TestChoiceID = createChoice();
	
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

   
    @Test
    public void testShouldBeOk() {
    	String username = "Jack";
    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(TestChoiceID, username, password);
		
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
    	String username = "Jack";
    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(TestChoiceID, username, password);
		
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
    	String username = "Jack";
    	String password = "hey";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(TestChoiceID, username, password);
		
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
    	String username = "Larry";
    	String username1 = "Harry";
    	String username2 = "Barry";
    	String username3 = "Karry";

    	String password = "";
		
    	ParticipateInChoiceRequest lr = new ParticipateInChoiceRequest(TestChoiceID, username, password);
    	ParticipateInChoiceRequest lr1 = new ParticipateInChoiceRequest(TestChoiceID, username1, password);
    	ParticipateInChoiceRequest lr2 = new ParticipateInChoiceRequest(TestChoiceID, username2, password);
    	ParticipateInChoiceRequest lr3 = new ParticipateInChoiceRequest(TestChoiceID, username3, password);

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
