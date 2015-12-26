package at.herzog.mailservice.json.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.herzog.mailservice.json.model.api.AbstractJsonModel;

public class SimpleMailRequest extends AbstractJsonModel {

	@JsonProperty("emails")
	@NotNull
	@Size(
			min = 1)
	private List<String> emails;

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("body")
	private String body;

	@JsonProperty("attachments")
	private List<Attachment> attachments = new ArrayList<>();

	public SimpleMailRequest() {
		super();
	}

	// ###############################################
	// Getter and Setter
	// ###############################################
	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

}
