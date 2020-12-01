package com.julia.http;

import java.util.ArrayList;
import java.util.List;

import com.julia.model.Alternative;
import com.julia.model.Choice;

public class CreateChoiceRequest {
	public String description;
	public List<String> alternatives;
	public int maxParticipants;
		
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<String> alternatives) {
		this.alternatives = alternatives;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public CreateChoiceRequest(String description, List<String> alternatives, int maxParticipants){
		this.description = description;
		this.alternatives = alternatives;
		this.maxParticipants = maxParticipants;
	}
	
	public CreateChoiceRequest() {		
	}
	
	public String toString() {
		return "Create Choice( " + description + ", with "+ Integer.toString(alternatives.size()) + " alternatives, and " + Integer.toString(maxParticipants) + " participants )";
	}
}
