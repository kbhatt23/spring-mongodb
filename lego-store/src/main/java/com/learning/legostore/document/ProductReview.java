package com.learning.legostore.document;

public class ProductReview {

	private String userName;
	
	private int rating;

	public ProductReview() {
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ProductReview(String userName, int rating) {
		this.userName = userName;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ProductReview [userName=" + userName + ", rating=" + rating + "]";
	}

	public String getUserName() {
		return userName;
	}

	public int getRating() {
		return rating;
	}
}
