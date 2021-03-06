package com.stripe.android.androidtreatcar.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.stripe.android.Stripe;
import com.stripe.android.exception.CardException;
import com.stripe.android.exception.StripeException;
import com.stripe.android.model.Charge;
import com.stripe.android.model.Token;

public class StripeTest extends AndroidTestCase {
	static Map<String, Object> defaultCardParams = new HashMap<String, Object>();
	static Map<String, Object> defaultChargeParams = new HashMap<String, Object>();
	static Map<String, Object> defaultCustomerParams = new HashMap<String, Object>();
	static Map<String, Object> defaultPlanParams = new HashMap<String, Object>();
	static Map<String, Object> defaultCouponParams = new HashMap<String, Object>();
	static Map<String, Object> defaultTokenParams = new HashMap<String, Object>();

	static String getUniquePlanId() {
		return String.format("JAVA-PLAN-%s", UUID.randomUUID());
	}

	static String getUniqueCouponId() {
		return String.format("JAVA-COUPON-%s", UUID.randomUUID());
	}

	static Map<String, Object> getUniquePlanParams() {
		Map<String, Object> uniqueParams = new HashMap<String, Object>();
		uniqueParams.putAll(defaultPlanParams);
		uniqueParams.put("id", getUniquePlanId());
		return uniqueParams;
	}

	static Map<String, Object> getUniqueCouponParams() {
		Map<String, Object> uniqueParams = new HashMap<String, Object>();
		uniqueParams.putAll(defaultCouponParams);
		uniqueParams.put("id", getUniqueCouponId());
		return uniqueParams;
	}

	public void setUp() {
		Stripe.apiKey = "tGN0bIwXnHdwOa85VABjPdSn8nWY7G7I"; // stripe public
															// test key

		defaultCardParams.put("number", "4242424242424242");
		defaultCardParams.put("exp_month", 12);
		defaultCardParams.put("exp_year", 2015);
		defaultCardParams.put("cvc", "123");
		defaultCardParams.put("name", "Java Bindings Cardholder");
		defaultCardParams.put("address_line1", "522 Ramona St");
		defaultCardParams.put("address_line2", "Palo Alto");
		defaultCardParams.put("address_zip", "94301");
		defaultCardParams.put("address_state", "CA");
		defaultCardParams.put("address_country", "USA");

		defaultChargeParams.put("amount", 100);
		defaultChargeParams.put("currency", "usd");
		defaultChargeParams.put("card", defaultCardParams);

		defaultTokenParams.put("card", defaultCardParams);

		defaultCustomerParams.put("card", defaultCardParams);
		defaultCustomerParams.put("description", "Java Bindings Customer");

		defaultPlanParams.put("amount", 100);
		defaultPlanParams.put("currency", "usd");
		defaultPlanParams.put("interval", "month");
		defaultPlanParams.put("name", "Java Bindings Plan");

		defaultCouponParams.put("duration", "once");
		defaultCouponParams.put("percent_off", 10);
	}

	public void testChargeCreate() throws StripeException {
		Charge createdCharge = Charge.create(defaultChargeParams);
		assertFalse(createdCharge.getRefunded());
	}

	public void testChargeRetrieve() throws StripeException {
		Charge createdCharge = Charge.create(defaultChargeParams);
		Charge retrievedCharge = Charge.retrieve(createdCharge.getId());
		assertEquals(createdCharge.getCreated(), retrievedCharge.getCreated());
		assertEquals(createdCharge.getId(), retrievedCharge.getId());
	}


	public void testInvalidCard() throws StripeException {
		try {
			Map<String, Object> invalidChargeParams = new HashMap<String, Object>();
			invalidChargeParams.putAll(defaultChargeParams);
			Map<String, Object> invalidCardParams = new HashMap<String, Object>();
			invalidCardParams.put("number", "4242424242424241");
			invalidCardParams.put("exp_month", 12);
			invalidCardParams.put("exp_year", 2015);
			invalidChargeParams.put("card", invalidCardParams);
			Charge.create(invalidChargeParams);
			Assert.fail("Should have thrown Card Exception");
		} catch (CardException e) {
			// success
		}
	}

	public void testTokenCreate() throws StripeException {
		Token token = Token.create(defaultTokenParams);
		assertFalse(token.getUsed());
	}

	public void testTokenRetrieve() throws StripeException {
		Token createdToken = Token.create(defaultTokenParams);
		Token retrievedToken = Token.retrieve(createdToken.getId());
		assertEquals(createdToken.getId(), retrievedToken.getId());
	}

	public void testTokenUse() throws StripeException {
		Token createdToken = Token.create(defaultTokenParams);
		Map<String, Object> chargeWithTokenParams = new HashMap<String, Object>();
		chargeWithTokenParams.put("amount", 199);
		chargeWithTokenParams.put("currency", "usd");
		chargeWithTokenParams.put("card", createdToken.getId());
		Charge.create(chargeWithTokenParams);
		Token retrievedToken = Token.retrieve(createdToken.getId());
		assertTrue(retrievedToken.getUsed());
	}

	/**
	 * Ensure the provided parameter for API key is actually being used. All
	 * other PerCallAPIKey methods assume this part works.
	 * 
	 * @throws StripeException
	 */
	public void testPerCallAPIUsage() throws StripeException {
		Charge createdCharge = Charge
				.create(defaultChargeParams, Stripe.apiKey);
		assertFalse(createdCharge.getRefunded());
		try {
			Charge.create(defaultChargeParams, "INVALID_KEY_HERE");
			fail();
		} catch (Exception e) {
		}
	}

	public void testChargeCreatePerCallAPIKey() throws StripeException {
		Charge createdCharge = Charge
				.create(defaultChargeParams, Stripe.apiKey);
		assertFalse(createdCharge.getRefunded());
	}

	public void testChargeRetrievePerCallAPIKey() throws StripeException {
		Charge createdCharge = Charge
				.create(defaultChargeParams, Stripe.apiKey);
		Charge retrievedCharge = Charge.retrieve(createdCharge.getId(),
				Stripe.apiKey);
		assertEquals(createdCharge.getCreated(), retrievedCharge.getCreated());
		assertEquals(createdCharge.getId(), retrievedCharge.getId());
	}

	public void testInvalidCardPerCallAPIKey() throws StripeException {
		try {
			Map<String, Object> invalidChargeParams = new HashMap<String, Object>();
			invalidChargeParams.putAll(defaultChargeParams);
			Map<String, Object> invalidCardParams = new HashMap<String, Object>();
			invalidCardParams.put("number", "4242424242424241");
			invalidCardParams.put("exp_month", 12);
			invalidCardParams.put("exp_year", 2015);
			invalidChargeParams.put("card", invalidCardParams);
			Charge.create(invalidChargeParams, Stripe.apiKey);
		} catch (CardException e) {
			// success
		}
	}

	public void testTokenCreatePerCallAPIKey() throws StripeException {
		Token token = Token.create(defaultTokenParams, Stripe.apiKey);
		assertFalse(token.getUsed());
	}

	public void testTokenRetrievePerCallAPIKey() throws StripeException {
		Token createdToken = Token.create(defaultTokenParams, Stripe.apiKey);
		Token retrievedToken = Token.retrieve(createdToken.getId(),
				Stripe.apiKey);
		assertEquals(createdToken.getId(), retrievedToken.getId());
	}

	public void testTokenUsePerCallAPIKey() throws StripeException {
		Token createdToken = Token.create(defaultTokenParams, Stripe.apiKey);
		Map<String, Object> chargeWithTokenParams = new HashMap<String, Object>();
		chargeWithTokenParams.put("amount", 199);
		chargeWithTokenParams.put("currency", "usd");
		chargeWithTokenParams.put("card", createdToken.getId());
		Charge.create(chargeWithTokenParams, Stripe.apiKey);
		Token retrievedToken = Token.retrieve(createdToken.getId(),
				Stripe.apiKey);
		assertTrue(retrievedToken.getUsed());
	}

}
