package com.julia.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Feedback {
	public final String idChoice;
	public final String idAlternative;
	public final String idMember;
	public final String description;
	public final String date;	
	
	public Feedback(String idChoice, String idAlternative, String idMember, String description) {
		this.idChoice = idChoice;
		this.idAlternative = idAlternative;
		this.idMember = idMember;
		this.description = description;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
		this.date = date.format(formatter);
	}
	
	public Feedback(String idMember, String description, String date) {
		this.idChoice = null;
		this.idAlternative = null;
		this.idMember = idMember;
		this.description = description;
		this.date = date;
	}
}
