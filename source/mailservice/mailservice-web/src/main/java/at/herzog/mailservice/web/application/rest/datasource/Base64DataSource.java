package at.herzog.mailservice.web.application.rest.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.FileTypeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import org.apache.commons.codec.binary.Base64;

import at.herzog.cdi.api.annotation.AttachmentType;
import at.herzog.mailservice.api.datasource.AbstractAttachmentDataSource;
import at.herzog.mailservice.json.model.Attachment;
import at.herzog.mailservice.web.application.rest.datasource.util.SharedByteArrayInputStream;

@Dependent
@AttachmentType("base64")
public class Base64DataSource extends AbstractAttachmentDataSource {

	private String name = "";
	private String contentType = "";
	private byte[] data = new byte[0];

	public Base64DataSource() {
	}

	@PostConstruct
	public void postConstruct() {

	}

	@Override
	public void init(Attachment attachment) {
		super.init(attachment);

		this.data = Base64.decodeBase64(attachment.getContent());
		this.contentType = FileTypeMap.getDefaultFileTypeMap().getContentType(attachment.getName());
		final String attName = attachment.getName();
		this.name = attName.substring(0, attName.indexOf("."));
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
