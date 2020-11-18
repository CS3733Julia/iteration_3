package com.julia.iteration_1.http;

public class CreateChoiceResponse {
	public final String idChoice;
	public final int statusCode;
	public final String error;

	public CreateChoiceResponse(String idChoice, int statusCode) {
		this.idChoice = idChoice;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public CreateChoiceResponse(String idChoice, int statusCode, String errorMessage) {
		this.idChoice = idChoice;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode == 200) {
			return "Result(" + idChoice + ")";
		} else {
			return "ErrorResult(" + statusCode + ")";
		}
	}
}
