package com.learning.legostore.document;

import java.time.LocalDate;

public class Deliveryinfo {
	private LocalDate deliveryDate;

	private double deliveryFee;

	private boolean instock;

	public Deliveryinfo() {
	}

	public Deliveryinfo(LocalDate deliveryDate, double deliveryFee, boolean instock) {
		this.deliveryDate = deliveryDate;
		this.deliveryFee = deliveryFee;
		this.instock = instock;
	}

	public double getDeliveryFee() {
		return deliveryFee;
	}

	public boolean isInstock() {
		return instock;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	@Override
	public String toString() {
		return "Deliveryinfo [deliveryDate=" + deliveryDate + ", deliveryFee=" + deliveryFee + ", instock=" + instock
				+ "]";
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public void setInstock(boolean instock) {
		this.instock = instock;
	}

}
