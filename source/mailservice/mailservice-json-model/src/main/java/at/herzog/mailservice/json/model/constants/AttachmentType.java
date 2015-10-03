package at.herzog.mailservice.json.model.constants;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("attachmentTypeEnum")
public enum AttachmentType {
	BASE64(
			"base64"),
	FILESYSTEM(
			"filesystem"),
	CUSTOM(
			"custom");

	public final String name;

	private AttachmentType(String name) {
		this.name = name;
	}
}
