package at.herzog.mailservice.json.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.herzog.mailservice.json.model.api.AbstractJsonModel;

public class RestResponse extends AbstractJsonModel {

	@JsonProperty("response")
	private String response;

	@JsonProperty("errors")
	private List<Error> errors = new ArrayList<>();

	public RestResponse() {
		super();
	}

	public void addError(final Error error) {
		errors.add(error);
	}

	// #######################################
	// Getter and Setter
	// #######################################
	public String getResponse() {
		return response;
	}

	public void setResponse(String message) {
		this.response = message;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
