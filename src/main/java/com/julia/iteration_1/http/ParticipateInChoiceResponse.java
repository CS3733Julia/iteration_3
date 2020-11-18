package com.julia.iteration_1.http;

import com.julia.iteration_1.model.Choice;

public class ParticipateInChoiceResponse {
	public final Choice choice;
	public final int statusCode;
	public final String error;

	
	public ParticipateInChoiceResponse(Choice choice, int statusCode) {
		this.choice = choice;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public ParticipateInChoiceResponse(int statusCode, String errorMessage) {
		this.choice = null;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString(){
		if (statusCode == 200) {
			return "Get Choice(" + choice.idChoice + ")";
		} else {
			return "Invalid Request(" + statusCode + ")";
		}
	}
}
