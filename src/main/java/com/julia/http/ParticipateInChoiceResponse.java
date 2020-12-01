package com.julia.http;

import com.julia.model.Choice;

public class ParticipateInChoiceResponse {
	public final Choice choice;
	public final int statusCode;
	public final String error;
	public final String idMember;

	
	public ParticipateInChoiceResponse(Choice choice, String idMember, int statusCode) {
		this.choice = choice;
		this.idMember = idMember;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public ParticipateInChoiceResponse(int statusCode, String errorMessage) {
		this.choice = null;
		this.idMember = null;
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
