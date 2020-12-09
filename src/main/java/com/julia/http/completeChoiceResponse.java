package com.julia.http;
import com.julia.model.Choice;


public class completeChoiceResponse {
	public final Choice choice;
	public final int statusCode;
	public final String error;
	
	public completeChoiceResponse(Choice choice, int statusCode) {
		this.choice = choice;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public completeChoiceResponse(String errorMessage, int statusCode) {
		this.choice = null;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString(){
		if (statusCode == 200) {
			return "Complete Choice(" + choice.idChoice + ")";
		} else {
			return "Invalid Request(" + statusCode + ")";
		}
	}
	
}
