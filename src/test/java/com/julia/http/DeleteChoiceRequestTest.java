package com.julia.http;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.julia.http.DeleteChoiceRequest;


public class DeleteChoiceRequestTest {
	
	@Test
	public void test() {
	String UUID = "s3fse";
	DeleteChoiceRequest dc = new DeleteChoiceRequest(UUID);
	assertEquals(dc.idChoice, "s3fse");
	}

}
