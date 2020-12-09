package com.julia;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.julia.http.DeleteNChoicesRequest;
import com.julia.http.DeleteNChoicesResponse;
import com.julia.http.GetAllChoicesResponse;
import com.julia.model.Choice;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetAllChoicesTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	GetAllChoicesLambda handler = new GetAllChoicesLambda();

    	GetAllChoicesResponse resp = handler.handleRequest(null, createContext("list"));
        
        for (Choice c : resp.list) {
        	System.out.println("found choice " + c.description);
        }
        Assert.assertEquals(200, resp.statusCode);
    }

}
