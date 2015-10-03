package at.herzog.mailservice.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.herzog.mailservice.json.model.api.AbstractJsonModel;

public class Error extends AbstractJsonModel {

	@JsonProperty("code")
	public final String code;

	@JsonProperty("message")
	public final String message;

	@JsonProperty("resolution")
	public final String resolution;

	public Error(@JsonProperty("code") String code, @JsonProperty("message") String message,
			@JsonProperty("resolution") String resolution) {
		super();
		this.code = code;
		this.message = message;
		this.resolution = resolution;
	}
}
