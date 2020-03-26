package com.learning.legostore.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PaymentMethod {
	@Id
	private String id;
	
	private String name;
	
	private double fee;
	
	private PaymentOption paymentOption;
	
	public PaymentMethod() {
	}

	public PaymentMethod(String name, double fee, PaymentOption paymentOption) {
		this.name = name;
		this.fee = fee;
		this.paymentOption = paymentOption;
	}

	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public PaymentOption getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(PaymentOption paymentOption) {
		this.paymentOption = paymentOption;
	}

	
}
