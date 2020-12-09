package com.julia;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.julia.http.DeleteNChoicesRequest;
import com.julia.http.DeleteNChoicesResponse;
import com.julia.model.Choice;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteNChoicesTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	DeleteNChoicesLambda handler = new DeleteNChoicesLambda();

    	DeleteNChoicesResponse resp = handler.handleRequest(new DeleteNChoicesRequest(5), createContext("list"));
        
        for (Choice c : resp.list) {
        	System.out.println("found choice " + c.description);
        }
        Assert.assertEquals(200, resp.statusCode);
    }

}
