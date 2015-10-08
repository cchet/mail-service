package at.herzog.mailservice.test.json.model;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.herzog.mailservice.json.model.Attachment;
import at.herzog.mailservice.json.model.SimpleMailRequest;
import at.herzog.mailservice.json.model.constants.AttachmentType;

@RunWith(JUnit4.class)
public class SimpleMailRequestTest {

	private ObjectMapper mapper;

	@Before
	public void beforeTest() {
		mapper = new ObjectMapper();
	}

	@Test
	public void allSet() throws Exception {
		// Given
		final SimpleMailRequest expected = new SimpleMailRequest();
		expected.setSubject("subject");
		expected.setBody("body");
		expected.setEmails(Arrays.asList(new String[] { "herzog.thomas81@gmail.com", "t.herzog@curecomp.com" }));
		expected.setAttachments(
				Arrays.asList(
						new Attachment[] {
								new Attachment("file1", AttachmentType.BASE64.name, "adsfgäsadfg"),
								new Attachment("file2", AttachmentType.CUSTOM.name, ""),
								new Attachment("file3", AttachmentType.FILESYSTEM.name, "") }));

		// When
		final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expected);
		System.out.println(json);
		final SimpleMailRequest actual = mapper.readValue(json, SimpleMailRequest.class);

		// Then
		expected.getSubject().equals(actual.getSubject());
		expected.getBody().equals(actual.getBody());
		expected.getEmails().equals(actual.getEmails());
		expected.getAttachments().equals(actual.getAttachments());
		expected.getUnknown().equals(actual.getUnknown());
	}
}
