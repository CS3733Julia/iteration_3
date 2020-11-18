package com.julia.iteration_1.http;

public class DeleteChoiceResponse {
	public final String idChoice;
	public final int statusCode;
	public final String error;

	public DeleteChoiceResponse (String idChoice, int statusCode) {
		this.idChoice = idChoice;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public DeleteChoiceResponse (String idChoice, int statusCode, String errorMessage) {
		this.idChoice = idChoice;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode == 200) {  // too cute?
			return "DeleteResponse(" + idChoice + ")";
		} else {
			return "ErrorResult(" + idChoice + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
