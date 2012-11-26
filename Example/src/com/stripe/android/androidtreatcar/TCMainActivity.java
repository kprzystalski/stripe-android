package com.stripe.android.androidtreatcar;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stripe.android.Stripe;
import com.stripe.android.exception.StripeException;
import com.stripe.android.model.Charge;
import com.stripe.android.utils.CVCNumberEditText;
import com.stripe.android.utils.CardNumberEditText;
import com.stripe.android.utils.MonthEditText;
import com.stripe.android.utils.YearEditText;
/**
 * Simple demo Activity.
 * To use validation of credit card number, cvc, month and year you should use custom EditText widgets:
 * <br />CardNumberEditText, MonthEditText, YearEditText, CVCNumberEditText 
 * <br /><br />To use them in layout you have to type full class name e.g. com.stripe.android.utils.CardNumberEditText 
 */
public class TCMainActivity extends Activity {
	private Button mOrderButton;
	private EditText mTreatCarTextField;
	private CardNumberEditText mNumberTextField;
	private MonthEditText mExpMonthTextField;
	private YearEditText mExpYearTextField;
	private CVCNumberEditText mCVCTextField;
	private TextView mAmountTextView;
	
	private Map<String, Object> mChargeMap;
	private Map<String, Object> mCardMap;
	
	private final float VALUE = 1f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Replace api key for your own use
		Stripe.apiKey = "sk_test_VSFDHz4eEScE7qlXUOgytfe4";
		setContentView(R.layout.tcmain_activity_layout);
		
		mChargeMap = new HashMap<String, Object>();
		mCardMap = new HashMap<String, Object>();
		
		setupUIInstances();
		setupEditTextListeners();
		setupButtonListeners();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCardMap = null;
		mChargeMap = null;
		System.gc();
	}
	
	/**
	 * Sets up UI variables.
	 */
	private void setupUIInstances() {
		mOrderButton = (Button) findViewById(R.id.orderButton);
		mTreatCarTextField = (EditText) findViewById(R.id.treatCarTextField);
		mNumberTextField = (CardNumberEditText) findViewById(R.id.numberTextField);
		mExpMonthTextField = (MonthEditText) findViewById(R.id.expMonthTextField);
		mExpYearTextField = (YearEditText) findViewById(R.id.expYearTextField);
		mCVCTextField = (CVCNumberEditText) findViewById(R.id.cvcTextField);
		mAmountTextView = (TextView) findViewById(R.id.textView1);
	}
	/**
	 * Private method used to reset fields
	 */
	private void resetErrors() {
		mNumberTextField.setBackgroundResource(android.R.drawable.edit_text);
		mCVCTextField.setBackgroundResource(android.R.drawable.edit_text);
		mExpMonthTextField.setBackgroundResource(android.R.drawable.edit_text);
		mExpYearTextField.setBackgroundResource(android.R.drawable.edit_text);
	}
	
	/**
	 * Private method invoking fields validation
	 */
	private boolean validateFields() {
		boolean validated = true;
		
		if(!mNumberTextField.isValidCardNumber()) {
			mNumberTextField.setBackgroundResource(R.drawable.textfield_search_empty_pressed);
			validated = false;
		}
		if(!mCVCTextField.isValidCVCNumber(mNumberTextField.getType())) {
			mCVCTextField.setBackgroundResource(R.drawable.textfield_search_empty_pressed);
			validated = false;
		}
		if(!mExpMonthTextField.isValidMonth()) {
			mExpMonthTextField.setBackgroundResource(R.drawable.textfield_search_empty_pressed);
			validated = false;
		}
		if(!mExpYearTextField.isValidDate(mExpMonthTextField.getMonth())) {
			mExpYearTextField.setBackgroundResource(R.drawable.textfield_search_empty_pressed);
			mExpMonthTextField.setBackgroundResource(R.drawable.textfield_search_empty_pressed);
			validated = false;
		}
		
		return validated;
	}

	/**
	 * Adds a button onClick event to send the request.
	 */
	private void setupButtonListeners() {
		mOrderButton.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {
				resetErrors();
				
				if(!validateFields()) return;
				
				mChargeMap.put("amount", (Long.valueOf(mTreatCarTextField.getText().toString()) * (long)VALUE));
				mChargeMap.put("currency", "usd");
				mCardMap.put("number", mNumberTextField.getText().toString());
				mCardMap.put("exp_month", Integer.valueOf(mExpMonthTextField.getText().toString()));
				mCardMap.put("exp_year", Integer.valueOf(mExpYearTextField.getText().toString()));
				mChargeMap.put("card", mCardMap);

				new AsyncTask<Map<String, Object>, Void, Charge>() {
					Charge tCharge = null;

					/**
					 * Charge.create should be done in async task, because it uses HTTPS connection.
					 * All methods using internet connection should be used in Async Task in Android.
					 */
					@Override
					protected Charge doInBackground(Map<String, Object>... param) {
						try {
							tCharge = Charge.create(param[0]);
							System.out.println(tCharge);
						} catch (StripeException e) {
							e.printStackTrace();
						}
						return tCharge;
					}
				}.execute(mChargeMap);
			}
		});
	}
	/**
	 * Sets text listener to update price in textview.
	 */
	private void setupEditTextListeners() {
		mTreatCarTextField.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				if(!arg0.toString().equals("")) {
					mAmountTextView.setText(NumberFormat.getCurrencyInstance(Locale.US).format((Double.valueOf(arg0.toString()) * VALUE) / 100));
				} else {
					mAmountTextView.setText("$0.00");
				}
			}
		});
	}
}
