package com.julia.http;

public class AddFeedbackRequest {
	public String idChoice;
	public String idAlternative;
	public String idMember;
	public String description;

	public AddFeedbackRequest(String idChoice, String idAlternative, String idMember, String description) {
		this.idChoice = idChoice;
		this.idAlternative = idAlternative;
		this.idMember = idMember;
		this.description = description;
	}
	
	public AddFeedbackRequest() {
		
	}
	
	public String toString() {
		return "Feedback Request( idChoice:" + idChoice + ", idAlternative:" + idAlternative + ", idMember:" + idMember + ", description:" + description + " )";
	}
}
