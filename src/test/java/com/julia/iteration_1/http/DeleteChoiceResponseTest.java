package com.julia.iteration_1.http;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeleteChoiceResponseTest {
	@Test
	public void test() {
		DeleteChoiceResponse dr = new DeleteChoiceResponse("ID", 200);
		String rep = dr.toString();
		assertTrue(rep.startsWith("DeleteResponse"));
		
		DeleteChoiceResponse drErr = new DeleteChoiceResponse("ID", 400);
		rep = drErr.toString();
		assertTrue(rep.startsWith("ErrorResult"));
	}
}
