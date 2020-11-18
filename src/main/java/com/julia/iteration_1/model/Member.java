package com.julia.iteration_1.model;

import java.util.UUID;

public class Member {
	public final String idMember;
	public final String username;
	private final String password;
	
	public Member(String username) {
		this.idMember = UUID.randomUUID().toString();
		this.username = username;
		this.password = null;
	}
	
	public Member(String username, String password) {
		this.idMember = UUID.randomUUID().toString();
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
}
