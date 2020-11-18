package com.julia.iteration_1.http;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.julia.iteration_1.model.Alternative;

public class CreateChoiceRequestTest {
	@Test
	public void test() {
		String testdesc = "this is a test";
		ArrayList<String> testalt = new ArrayList<String>();
		testalt.add("tesing1");
		testalt.add("tesing2");
		int maxParticipants = 5;
		
		CreateChoiceRequest cc = new CreateChoiceRequest(testdesc, testalt, maxParticipants);
		
		assertEquals(cc.description, testdesc);
		String testA = cc.alternatives.get(1);
		assertEquals(testA, "tesing2");
		assertEquals(cc.maxParticipants, 5);
	}
}
