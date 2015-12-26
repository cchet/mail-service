package at.herzog.mailservice.web.application.config.rest.event;

public class RemoveAttachmentDataSourceEvt extends AbstractDataSourceEvt {

	private final String type;

	public RemoveAttachmentDataSourceEvt(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getName()).append("[type=").append(type).append(" | success=")
				.append(isSuccess()).append("]").toString();
	}
}
