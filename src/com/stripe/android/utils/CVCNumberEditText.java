package com.stripe.android.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class CVCNumberEditText extends EditText {

	public CVCNumberEditText(Context context) {
		super(context);
	}

	public CVCNumberEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CVCNumberEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public boolean isValidCVCNumber(CreditCardTypes pCreditCardType) {
		if(getText().toString().equals("")) {
			return false;
		}
		String tCVCNumber = StripeTextUtils.removeWhitespaces(getText().toString());
		if(!StripeTextUtils.isNumericOnly(tCVCNumber)) {
			return false;
		}
		if(pCreditCardType.equals(CreditCardTypes.AMERICAN_EXPRESS) && (tCVCNumber.length() != 4)) {
			return false;
		} else if(tCVCNumber.length() != 3) {
			return false;
		}
		
		return true;
	}

}
