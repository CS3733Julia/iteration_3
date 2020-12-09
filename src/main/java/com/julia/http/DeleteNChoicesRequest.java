package com.julia.http;

public class DeleteNChoicesRequest {
	public int days;
	
	public DeleteNChoicesRequest() {	
	}
	
	public DeleteNChoicesRequest(int days) {
		this.days = days;
	}
	
	public void setDays(int days) {
		this.days = days;
	}

	public String toString() {
		return "Delete( Choices before" + days + " days)";
	}
}
