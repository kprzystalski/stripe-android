package com.stripe.android.model;

import java.util.List;

import com.stripe.android.exception.StripeException;
import com.stripe.android.net.APIResource;

public class Account extends APIResource {
	String id;
	Boolean chargeEnabled;
	Boolean detailsSubmitted;
	List<String> currenciesSupported;
	String email;
	String statementDescriptor;

	public String getId() {
		return id;
	}

	public Boolean getChargeEnabled() {
		return chargeEnabled;
	}

	public Boolean getDetailsSubmitted() {
		return detailsSubmitted;
	}

	public List<String> getCurrenciesSupported() {
		return currenciesSupported;
	}

	public String getEmail() {
		return email;
	}

	public String getStatementDescriptor() {
		return statementDescriptor;
	}

  public static Account retrieve() throws StripeException {
    return retrieve(null);
	}

  public static Account retrieve(String apiKey) throws StripeException {
		return request(RequestMethod.GET, singleClassURL(Account.class), null, Account.class, apiKey);
	}
}
