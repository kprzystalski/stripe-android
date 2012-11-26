package com.stripe.android.utils;

import java.util.regex.Pattern;

public class StripeTextUtils {
	public static String removeWhitespaces(String pString) {
		return pString.replaceAll("[\\s-]*", "");
	}
	
	public static boolean isNumericOnly(String pString) {
		return Pattern.matches("[0-9[-\\s]]+", pString);
	}

}
