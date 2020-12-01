package com.julia.http;

public class ApproveRequest {
	public String idAlternative;
	public String idMember;
	
	public ApproveRequest(String idAlternative, String idMember) {
		this.idAlternative = idAlternative;
		this.idMember = idMember;
	}
	
	public ApproveRequest() {
		
	}
	
	public String toString() {
		return "Approve Request( idAlternative:" + idAlternative + ", idMember:" + idMember + " )";
	}
}
