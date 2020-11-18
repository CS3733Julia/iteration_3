package com.julia.iteration_1.http;

public class DeleteChoiceRequest {
	public String idChoice;
	
	public DeleteChoiceRequest() {	
	}
	
	public DeleteChoiceRequest(String idChoice) {
		this.idChoice = idChoice;
	}

	public String toString() {
		return "Delete(" + idChoice + ")";
	}
}
