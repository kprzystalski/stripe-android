package com.stripe.android.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class CardNumberEditText extends EditText {

	public CardNumberEditText(Context context) {
		super(context);
	}

	public CardNumberEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardNumberEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	
	public boolean isValidCardNumber() {
		if(getText().toString().equals("")) {
			return false;
		}
		String tCardNumber = StripeTextUtils.removeWhitespaces(getText().toString());
		if(!StripeTextUtils.isNumericOnly(tCardNumber)) {
			return false;
		} 
		if(!isLughValidString(tCardNumber)) {
			return false;
		}
		if(tCardNumber.length() < 10 || tCardNumber.length() > 19) {
			return false;
		}
		return true;
	}
	
	private boolean isLughValidString(String pString) {
		boolean isOdd = true;
		int sum = 0;
		
		for(int i = pString.length() - 1; i >= 0; --i) {
			int digit = Integer.valueOf(pString.substring(i, i + 1));
			isOdd = !isOdd;
			if(isOdd) {
				digit *= 2;
			}
			if(digit > 9) {
				digit -= 9;
			}
			sum += digit;
		}
		
		if(sum % 10 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public CreditCardTypes getType() {
		if(getText().toString().equals("")) {
			return CreditCardTypes.UNKNOWN;
		}
		String tCardNumber = StripeTextUtils.removeWhitespaces(getText().toString());
		String tCardNumberPrefix = tCardNumber.substring(0, 2);
		if(tCardNumberPrefix.equals("34") || tCardNumberPrefix.equals("37")) {
			return CreditCardTypes.AMERICAN_EXPRESS;
		} else if(tCardNumberPrefix.equals("60") || tCardNumberPrefix.equals("62") || tCardNumberPrefix.equals("64") || tCardNumberPrefix.equals("65")) {
			return CreditCardTypes.DISCOVER;
		} else if(tCardNumberPrefix.equals("35")) {
			return CreditCardTypes.JCB;
		} else if(tCardNumberPrefix.equals("30") || tCardNumberPrefix.equals("36") || tCardNumberPrefix.equals("38") || tCardNumberPrefix.equals("39")) {
			return CreditCardTypes.DINERS_CLUB;
		} else if(tCardNumberPrefix.substring(0, 1).equals("4")) {
			return CreditCardTypes.VISA;
		} else if(tCardNumberPrefix.substring(0, 1).equals("5")) {
			return CreditCardTypes.MASTER_CARD;
		} else {
			return CreditCardTypes.UNKNOWN;
		}
	}
}
