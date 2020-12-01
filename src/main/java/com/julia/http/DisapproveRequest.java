package com.julia.http;

public class DisapproveRequest {
	public String idAlternative;
	public String idMember;
	
	public DisapproveRequest(String idAlternative, String idMember) {
		this.idAlternative = idAlternative;
		this.idMember = idMember;
	}
	
	public DisapproveRequest() {
		
	}
	
	public String toString() {
		return "Disapprove Request( idAlternative:" + idAlternative + ", idMember:" + idMember + " )";
	}
}
