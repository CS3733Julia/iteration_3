package com.julia.http;

import java.util.ArrayList;
import java.util.List;

import com.julia.model.Choice;

public class GetAllChoicesResponse {
	public final List<Choice> list;
	public final int statusCode;
	public final String error;
	
	public GetAllChoicesResponse (List<Choice> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public GetAllChoicesResponse (int code, String errorMessage) {
		this.list = new ArrayList<Choice>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyChoices"; }
		return "AllChoices(" + list.size() + ")";
	}
}
