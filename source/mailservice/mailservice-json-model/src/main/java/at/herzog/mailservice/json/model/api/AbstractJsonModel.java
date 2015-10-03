package at.herzog.mailservice.json.model.api;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(
		allowGetters = false,
		allowSetters = false,
		ignoreUnknown = true)
@JsonInclude(
		content = Include.NON_EMPTY,
		value = Include.NON_EMPTY)
public abstract class AbstractJsonModel {

	protected Map<String, Object> unknown = new HashMap<>();

	@JsonAnySetter
	public void setUnknown(final Map<String, Object> unknown) {
		this.unknown = unknown;
	}

	@JsonAnyGetter
	public Map<String, Object> getUnknown() {
		return unknown;
	}
}
