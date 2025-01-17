package com.julia.http;

public class ParticipateInChoiceRequest {
	public String idChoice;
	public String username;
	public String password;
	
	public ParticipateInChoiceRequest(String idChoice, String username, String password) {
		this.idChoice = idChoice;
		this.username = username;
		this.password = password;
	}
	
	public ParticipateInChoiceRequest(String idChoice, String username) {
		this.idChoice = idChoice;
		this.username = username;
		this.password = "";
	}
	
	public ParticipateInChoiceRequest() {
		
	}
	
	public String toString() {
		if (password != "") {
			return "Get Choice( idChoice:" + idChoice + ", username:" + username + " )";
		}
		else {
			return "Get Choice( idChoice:" + idChoice + ", username:" + username + ", password:" + password + " )";
		}
	}
}
