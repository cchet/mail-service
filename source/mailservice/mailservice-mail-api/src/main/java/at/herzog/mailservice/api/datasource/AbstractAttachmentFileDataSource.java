package at.herzog.mailservice.api.datasource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.FileDataSource;

import at.herzog.mailservice.json.model.Attachment;

public abstract class AbstractAttachmentFileDataSource extends AbstractAttachmentDataSource {

	protected FileDataSource dataSource;

	public AbstractAttachmentFileDataSource() {
		super();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return dataSource.getOutputStream();
	}

	public InputStream getInputStream() throws IOException {
		return dataSource.getInputStream();
	};

	public String getContentType() {
		return dataSource.getContentType();
	};

	@Override
	public String getName() {
		return dataSource.getName();
	}

	/**
	 * Subclass shall return the file represented by the string key.
	 * 
	 * @param key
	 *            the key of the file
	 * @return the File instance pointing to the file represented by the key
	 * @throws FileNotFoundException
	 *             if the file could not be found
	 */
	public abstract File getFile(String key);

	/**
	 * Inits this data source with the supported attachment.<br>
	 * Always call the super implementation if you overwrite this method.
	 * 
	 * @param attachment
	 *            the attachment with provides information for loading the data
	 */
	public void init(final Attachment attachment) {
		super.init(attachment);

		try {
			final File file = this.getFile(attachment.getName());
			if (file != null) {
				throw new FileNotFoundException("File must not be null");
			}
			this.dataSource = new FileDataSource(file);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Cannot load data for null file", e);
		}
	}
}
