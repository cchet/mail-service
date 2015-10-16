package at.herzog.mailservice.api.context;

import java.util.Properties;

import javax.mail.Authenticator;

public class ConnectionContext {

	public static enum Protocol {
		POP3(
				"pop3"),
		SMTP(
				"smtp");

		public final String protocol;

		private Protocol(String protocol) {
			this.protocol = protocol;
		}

	}

	private String host;
	private Integer port;
	private Protocol protocol;
	private Authenticator authenticator;
	private boolean useTls;

	public ConnectionContext(String host, Integer port, Protocol protocol, Authenticator authenticator,
			boolean useTls) {
		super();
		this.host = host;
		this.port = port;
		this.protocol = protocol;
		this.authenticator = authenticator;
		this.useTls = useTls;
	}

	public ConnectionContext() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Properties generateConnectionProperties() {
		final Properties properties = new Properties();
		final String prot = protocol.protocol;
		properties.put("mail." + prot + ".host", host);
		properties.put("mail." + prot + ".port", port);
		properties.put("mail." + prot + ".starttls.enable", useTls);
		properties.put("mail." + prot + ".auth", useTls);
		if (authenticator != null) {
			properties.put("mail." + prot + ".auth", Boolean.TRUE);
		}
		return properties;
	}

	// ##################################################
	// Getter and Setter
	// ##################################################
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public boolean isUseTls() {
		return useTls;
	}

	public void setUseTls(boolean useTls) {
		this.useTls = useTls;
	}

}
