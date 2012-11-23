package com.stripe.android.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class MonthEditText extends EditText {

	public MonthEditText(Context context) {
		super(context);
	}

	public MonthEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MonthEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isValidMonth() {
		if(getText().toString().equals("")) {
			return false;
		}
		String tMonth = StripeTextUtils.removeWhitespaces(getText().toString());
		if(!StripeTextUtils.isNumericOnly(tMonth)) {
			return false;
		}
		if((Integer.valueOf(tMonth) <= 0) || (Integer.valueOf(tMonth) >=13)) {
			return false;
		}
		return true;
	}
	
	public int getMonth() {
		return Integer.valueOf(StripeTextUtils.removeWhitespaces(getText().toString()).equals("") ? "0" : StripeTextUtils.removeWhitespaces(getText().toString()));
	}
}
