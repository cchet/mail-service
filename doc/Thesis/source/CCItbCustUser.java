public class CCItbCustUser extends CCBasicEmail {
	
	private Map cache = new HashMap();

	public CCItbCustUser() {
		super();
	}

	public CCItbCustUser(CCMailingDAO dao) {
		super(dao);
	}

	@Override
	String getMailType() {
		return "ISCU";
	}
	
	@Override
	public void run() {
		try {
			sendEmailNoAttachement(getDAO().getItbStartCustUserMailText());
		} catch (DAOSysException ex) {
			LOG.error("DAOSysException in CCItbCustUser.run: ",
						ex);
		} finally {
			stopMe();
		}
	}
	
	@Override
	protected String getMailBody(String bodyKey, String bodySQLKey)
		throws DAOSysException {
		int lanId = ((CCItbVO)currVO).getLanguageId();
		int itbhId = ((CCItbVO)currVO).getItbhID();
		String body = "";
		String key = itbhId	+ "_" + lanId;
		if (cache.containsKey(key)) {
			body = (String) cache.get(key);
			LOG.debug("48: Got from cache key: " + key 
								+ " body: " + body);
		} else {
			Object [] allParams = getDAO().getItbCustData((CCItbVO)currVO, 19);
			// Message body parameters retrieved from result set for body template
			MessageFormat form = new MessageFormat(rb.getString(bodyKey).trim());
	 		body = form.format(params);
	 		cache.put(key, body);
	 		LOG.debug("48: DB access for the key: " + key
	 						+ " got body: " + body);
		}
		return body;
	}
}
