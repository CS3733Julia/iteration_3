package com.julia.http;

import java.util.ArrayList;
import java.util.List;

import com.julia.model.Choice;

public class DeleteNChoicesResponse {
	public final List<Choice> list;
	public final int statusCode;
	public final String error;

	public DeleteNChoicesResponse (List<Choice> list, int statusCode) {
		this.list = list;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public DeleteNChoicesResponse (int code, String errorMessage) {
		this.list = new ArrayList<Choice>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyChoices"; }
		return "AllChoices(" + list.size() + ")";
	}
}
