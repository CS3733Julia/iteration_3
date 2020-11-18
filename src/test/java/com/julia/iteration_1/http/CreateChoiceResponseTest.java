package com.julia.iteration_1.http;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.julia.iteration_1.model.Alternative;

public class CreateChoiceResponseTest {

	@Test
	public void test() {
		CreateChoiceResponse cr = new CreateChoiceResponse("ID", 200);
		String rep = cr.toString();
		assertTrue(rep.startsWith("Result"));
		
		CreateChoiceResponse crErr = new CreateChoiceResponse("ID", 400);
		rep = crErr.toString();
		assertTrue(rep.startsWith("ErrorResult"));
	}
}
