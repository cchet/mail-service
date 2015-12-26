package at.herzog.mailservice.test.json.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.herzog.mailservice.json.model.Attachment;
import at.herzog.mailservice.json.model.constants.AttachmentType;

@RunWith(JUnit4.class)
public class AttachmentTest {

	private ObjectMapper mapper;

	@Before
	public void beforeTest() {
		mapper = new ObjectMapper();
	}

	@Test
	public void allSet() throws Exception {
		// Given
		final Attachment expected = new Attachment();
		expected.setName("name_or_key");
		expected.setContent("content");
		expected.setType(AttachmentType.BASE64.name);

		// When
		final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expected);
		System.out.println(json);
		final Attachment actual = mapper.readValue(json, Attachment.class);

		// Then
		expected.getName().equals(actual.getName());
		expected.getContent().equals(actual.getContent());
		expected.getType().equals(actual.getType());
		expected.getUnknown().equals(actual.getUnknown());
	}
}
