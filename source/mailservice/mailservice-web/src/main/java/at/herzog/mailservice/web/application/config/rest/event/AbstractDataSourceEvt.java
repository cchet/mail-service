package at.herzog.mailservice.web.application.config.rest.event;

public abstract class AbstractDataSourceEvt {

	private boolean success = Boolean.FALSE;

	public AbstractDataSourceEvt() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}