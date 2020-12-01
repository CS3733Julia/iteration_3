package com.julia.http;

import com.julia.model.Choice;

public class DisapproveResponse {
	public final Choice choice;
	public final int statusCode;
	public final String error;

	
	public DisapproveResponse(Choice choice, int statusCode) {
		this.choice = choice;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public DisapproveResponse(String errorMessage, int statusCode) {
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
