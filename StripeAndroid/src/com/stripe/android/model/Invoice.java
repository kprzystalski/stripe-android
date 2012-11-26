package com.stripe.android.model;

import java.util.Map;

import com.stripe.android.exception.StripeException;
import com.stripe.android.net.APIResource;

public class Invoice extends APIResource {
	Integer subtotal;
	Integer total;
	Integer amountDue;
	Integer startingBalance;
	Integer endingBalance;
	String id;
	Long created;
	Long nextPaymentAttempt;
	Boolean attempted;
	String charge;
	Boolean closed;
	String customer;
	Long date;
	Boolean paid;
	Long periodStart;
	Long periodEnd;
	Discount discount;
	InvoiceLineItemCollection lines;
	Boolean livemode;
	Integer attemptCount;
	String currency;

	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(Integer amountDue) {
		this.amountDue = amountDue;
	}

	public Integer getStartingBalance() {
		return startingBalance;
	}

	public void setStartingBalance(Integer startingBalance) {
		this.startingBalance = startingBalance;
	}

	public Integer getEndingBalance() {
		return endingBalance;
	}

	public void setEndingBalance(Integer endingBalance) {
		this.endingBalance = endingBalance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getNextPaymentAttempt() {
		return nextPaymentAttempt;
	}

	public void setNextPaymentAttempt(Long nextPaymentAttempt) {
		this.nextPaymentAttempt = nextPaymentAttempt;
	}

	public Boolean getAttempted() {
		return attempted;
	}

	public void setAttempted(Boolean attempted) {
		this.attempted = attempted;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public Long getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(Long periodStart) {
		this.periodStart = periodStart;
	}

	public Long getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(Long periodEnd) {
		this.periodEnd = periodEnd;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public InvoiceLineItemCollection getLines() {
		return lines;
	}

	public Boolean getLivemode() {
		return livemode;
	}

	public void setLivemode(Boolean livemode) {
		this.livemode = livemode;
	}

	public Integer getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public static Invoice retrieve(String id) throws StripeException {
		return retrieve(id, null);
	}

	public static Invoice create(Map<String, Object> params) throws StripeException {
		return create(params, null);
	}

	public static InvoiceCollection all(Map<String, Object> params) throws StripeException {
		return all(params, null);
	}

	public static Invoice upcoming(Map<String, Object> params) throws StripeException {
		return upcoming(params, null);
	}

	public Invoice pay() throws StripeException {
        return this.pay(null);
    }

    public Invoice update(Map<String, Object> params) throws StripeException {
    	return update(params, null);
    }

	public static Invoice retrieve(String id, String apiKey) throws StripeException {
		return request(RequestMethod.GET, instanceURL(Invoice.class, id), null, Invoice.class, apiKey);
	}

	public static Invoice create(Map<String, Object> params, String apiKey) throws StripeException {
		return request(RequestMethod.POST, classURL(Invoice.class), params, Invoice.class, apiKey);
	}

	public static Invoice upcoming(Map<String, Object> params, String apiKey) throws StripeException {
		return request(RequestMethod.GET, String.format("%s/upcoming", classURL(Invoice.class)),
				params, Invoice.class, apiKey);
	}

	public static InvoiceCollection all(Map<String, Object> params, String apiKey) throws StripeException {
		return request(RequestMethod.GET, classURL(Invoice.class), params, InvoiceCollection.class, apiKey);
	}

	public Invoice update(Map<String, Object> params, String apiKey) throws StripeException {
		return request(RequestMethod.POST, instanceURL(Invoice.class, this.id), params, Invoice.class, apiKey);
	}

	public Invoice pay(String apiKey) throws StripeException {
		return request(RequestMethod.POST,
				String.format("%s/pay", instanceURL(Invoice.class, this.getId())),
				null, Invoice.class, apiKey);
	}
}