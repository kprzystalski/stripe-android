package com.stripe.android;

import java.util.HashMap;
import java.util.Map;

import com.stripe.android.model.Charge;
import com.stripe.android.utils.CVCNumberEditText;
import com.stripe.android.utils.CardNumberEditText;
import com.stripe.android.utils.MonthEditText;
import com.stripe.android.utils.YearEditText;

public abstract class Stripe {
	public static final String API_BASE = "https://api.stripe.com";
	public static final String VERSION = "1.1.11";
	public static String apiKey;

	
}
