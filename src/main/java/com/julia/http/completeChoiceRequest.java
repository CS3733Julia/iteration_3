package com.julia.http;

public class completeChoiceRequest {
	public String idAlternative;
	
	public completeChoiceRequest(String idAlternative, String idMember) {
		this.idAlternative = idAlternative;
	}
	
	public completeChoiceRequest() {
		
	}
	
	public String toString() {
		return "Complete Choice Request( idAlternative:" + idAlternative + ")";
	}
}
