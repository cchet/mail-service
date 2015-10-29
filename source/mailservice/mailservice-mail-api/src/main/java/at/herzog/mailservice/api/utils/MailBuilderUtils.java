package at.herzog.mailservice.api.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class MailBuilderUtils {

	private MailBuilderUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Address[] emailsToAddresses(final Collection<String> emails) {
		if ((emails != null) && (!emails.isEmpty())) {
			final List<InternetAddress> addresses = new LinkedList<>();
			emails.forEach(new Consumer<String>() {
				@Override
				public void accept(String email) {
					try {
						final InternetAddress[] addressesArray = InternetAddress.parse(email);
						if (addressesArray.length == 1) {
							addresses.add(addressesArray[0]);
						}
					} catch (AddressException e) {
					}
				}
			});
			return addresses.toArray(new Address[addresses.size()]);
		}
		return new Address[0];
	}
}
