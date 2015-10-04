package at.herzog.mailservice.web.application.rest.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.FileTypeMap;

import org.apache.commons.codec.binary.Base64;

import at.herzog.mailservice.json.model.Attachment;
import at.herzog.mailservice.web.application.rest.datasource.api.AbstractAttachmentDataSource;
import at.herzog.mailservice.web.application.rest.datasource.api.SharedByteArrayInputStream;

public class Base64DataSource extends AbstractAttachmentDataSource {

	private String name;
	private String contentType;
	private byte[] data;

	public static final String TYPE = "base64";

	public Base64DataSource() {
		super(TYPE);
	}

	@Override
	public void init(Attachment attachment) {
		super.init(attachment);

		this.data = Base64.decodeBase64(attachment.getContent());
		this.contentType = FileTypeMap.getDefaultFileTypeMap().getContentType(attachment.getName());
		final String attName = attachment.getName();
		this.name = attName.substring(attName.indexOf("\\."), attName.length());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new SharedByteArrayInputStream(data);
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("Writing not supported");
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public String getName() {
		return name;
	}
}
