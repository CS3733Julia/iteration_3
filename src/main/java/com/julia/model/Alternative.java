package com.julia.model;

import java.util.ArrayList;
import java.util.UUID;

public class Alternative {
	public final String idAlternative;
	public final String descriptionAlternative;
	ArrayList<Feedback> feedback;
	ArrayList<String> approved;
	ArrayList<String> disapproved;
	public boolean isChosen;
	
	public Alternative(String description){
		this.idAlternative = UUID.randomUUID().toString();
		this.descriptionAlternative = description;
		this.feedback = null;
		this.approved = null;
		this.disapproved = null;
		this.isChosen = false;
	}
	
	//TODO: fix this for next iteration- it needs to take feedback, approved, and disapproved
	public Alternative(String idAlternative, String description, boolean isChosen){
		this.idAlternative = idAlternative;
		this.descriptionAlternative = description;
		this.feedback = null;
		this.approved = null;
		this.disapproved = null;
		this.isChosen = isChosen;	
	}
	
	public Alternative(String idAlternative, String description, boolean isChosen,	
					   ArrayList<String> approved, ArrayList<String> disapproved){
		this.idAlternative = idAlternative;
		this.descriptionAlternative = description;
		this.feedback = null;
		this.approved = approved;
		this.disapproved = disapproved;
		this.isChosen = isChosen;	
	}
	
	public Alternative(String idAlternative, String description, boolean isChosen,	
			   ArrayList<String> approved, ArrayList<String> disapproved, ArrayList<Feedback> feedback){
		this.idAlternative = idAlternative;
		this.descriptionAlternative = description;
		this.feedback = feedback;
		this.approved = approved;
		this.disapproved = disapproved;
		this.isChosen = isChosen;	
	}

	public ArrayList<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(ArrayList<Feedback> feedback) {
		this.feedback = feedback;
	}

	public ArrayList<String> getApproved() {
		return approved;
	}

	public void setApproved(ArrayList<String> approved) {
		approved = approved;
	}

	public ArrayList<String> getDisapproved() {
		return disapproved;
	}

	public void setDisapproved(ArrayList<String> disapproved) {
		disapproved = disapproved;
	}

	public boolean isChoosen() {
		return isChosen;
	}

	public void setChoosen(boolean isChosen) {
		this.isChosen = isChosen;
	}
}
