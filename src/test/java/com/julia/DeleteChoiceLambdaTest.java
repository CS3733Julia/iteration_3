package com.julia;

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
import com.julia.model.Alternative;


public class DeleteChoiceLambdaTest extends LambdaTest{
    @Test
    public void testCreateAndDeleteChoice() {
    	String testdesc = "this is a test";
    	ArrayList<String> testalt = new ArrayList<String>();
		testalt.add("tesing1");
		testalt.add("tesing2");
		int maxParticipants = 5;
		
		CreateChoiceRequest cc = new CreateChoiceRequest(testdesc, testalt, maxParticipants);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(cc);  
        
        CreateChoiceLambda handler = new CreateChoiceLambda();
    	CreateChoiceRequest req = new Gson().fromJson(SAMPLE_INPUT_STRING, CreateChoiceRequest.class);
       
    	CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.statusCode);
    	        
        // now delete
        DeleteChoiceRequest dcr = new DeleteChoiceRequest(resp.idChoice);
        DeleteChoiceResponse d_resp = new DeleteChoiceLambda().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(resp.idChoice, d_resp.idChoice);
        
        // try again and this should fail
        d_resp = new DeleteChoiceLambda().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(400, d_resp.statusCode);
    }
}
