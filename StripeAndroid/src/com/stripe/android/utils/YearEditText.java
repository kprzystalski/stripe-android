package com.stripe.android.utils;

import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class YearEditText extends EditText {

	public YearEditText(Context context) {
		super(context);
	}

	public YearEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public YearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isValidYear() {
		if(getText().toString().equals("")) {
			return false;
		}
		String tYear = StripeTextUtils.removeWhitespaces(getText().toString());
		if(!StripeTextUtils.isNumericOnly(tYear)) {
			return false;
		}
		if(Integer.valueOf(tYear) <= Calendar.getInstance().get(Calendar.YEAR)) {
			return false;
		}
		return true;
	}
	
	public boolean isValidDate(int pMonth) {
		if(!isValidYear()) {
			return false;
		}
		if(Calendar.getInstance().get(Calendar.YEAR) == Integer.valueOf(StripeTextUtils.removeWhitespaces(getText().toString()))) {
			if(Calendar.getInstance().get(Calendar.MONTH) > pMonth) {
				return false;
			}
		}
		return true;
	}
}
