package com.julia;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.julia.DisapproveAlternativeLambda;
import com.julia.http.ApproveRequest;
import com.julia.http.ApproveResponse;
import com.julia.http.DisapproveRequest;
import com.julia.http.DisapproveResponse;

public class DisapproveAlternativeLambdaTest extends LambdaTest {
    String testSuccessInput(String incoming) throws IOException {
    	DisapproveAlternativeLambda handler = new DisapproveAlternativeLambda();
    	DisapproveRequest req = new Gson().fromJson(incoming, DisapproveRequest.class);
       
    	DisapproveResponse resp = handler.handleRequest(req, createContext("approve"));
        Assert.assertEquals(200, resp.statusCode);
        return resp.choice.idChoice;
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	DisapproveAlternativeLambda handler = new DisapproveAlternativeLambda();
    	DisapproveRequest req = new Gson().fromJson(incoming, DisapproveRequest.class);

    	DisapproveResponse resp = handler.handleRequest(req, createContext("approve"));
        Assert.assertEquals(failureCode, resp.statusCode);
    }

	@Test
    public void testShouldBeOk() {
    	String idAlternative = "0336a1f5-5d8d-4c22-a276-fca06f556d07";
    	String idMember = "3225b467-f556-4737-af41-9df2f4967d52";
		
		DisapproveRequest ar = new DisapproveRequest(idAlternative, idMember);
		
        String SAMPLE_INPUT_STRING = new Gson().toJson(ar);  
        String idChoice = "";
        try {
        	idChoice = testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
