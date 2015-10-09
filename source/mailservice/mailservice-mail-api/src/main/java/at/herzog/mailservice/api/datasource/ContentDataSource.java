package at.herzog.mailservice.api.datasource;

public interface ContentDataSource {

	void init(String content);

	String getContent();

	String getContentType();
}
