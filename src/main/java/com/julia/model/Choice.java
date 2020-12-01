package com.julia.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class Choice implements Iterable<Alternative>{
	public final String idChoice;
	public final String description;
	public final List<Alternative> alternatives;
	public final int maxParticipants;
	public final String dateCreate;	
	public final String dateComplete;
	
	public Choice(String description, List<Alternative> alternatives, int maxParticipants) {
		this.idChoice = UUID.randomUUID().toString();
		this.description = description;
		this.alternatives = alternatives;
		this.maxParticipants = maxParticipants;	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateCreate = LocalDateTime.now();
		this.dateCreate = dateCreate.format(formatter);
		this.dateComplete = null;
	}
	
	public Choice(String idChoice, String description, List<Alternative> alternatives, int maxParticipants, String dateCreate, String dateComplete) {
		this.idChoice = idChoice;
		this.description = description;
		this.alternatives = alternatives;
		this.maxParticipants = maxParticipants;
		this.dateCreate = dateCreate;
		this.dateComplete = dateComplete;
	}
	
	@Override 
	public Iterator<Alternative> iterator(){
		return alternatives.iterator();
	}
}
