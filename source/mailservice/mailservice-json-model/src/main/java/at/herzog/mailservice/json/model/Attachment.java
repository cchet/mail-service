package at.herzog.mailservice.json.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import at.herzog.mailservice.json.model.api.AbstractJsonModel;
import at.herzog.mailservice.json.model.constants.AttachmentType;

public class Attachment extends AbstractJsonModel {

	@JsonProperty("name")
	@NotNull
	private String name;

	@JsonProperty("type")
	@NotNull
	@JsonTypeInfo(
			include = As.WRAPPER_OBJECT,
			use = Id.NAME)
	private AttachmentType type;

	@JsonProperty("content")
	private String content;

	public Attachment() {
		super();
	}

	public Attachment(String name, AttachmentType type, String content) {
		super();
		this.name = name;
		this.type = type;
		this.content = content;
	}

	// ##########################################
	// Getter and Setter
	// ##########################################
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AttachmentType getType() {
		return type;
	}

	public void setType(AttachmentType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
