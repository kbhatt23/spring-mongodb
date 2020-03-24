package com.learning.legostore.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
//mongo entity
@Document(collection = "legoSets")
//@Document
//by default it will create collection name as legoSet
public class LegoSet {
	@Id
	//can only use String or GUUID type in mongo DB for primary field
	private String id;
	public String getId() {
		return id;
	}

	//helps in filtering , and hence enhances in searching
	//should not create for all columns as during CRUD operation performance will degrade
	//IndexDirection.ASCENDING will sort the result in ascending order
	@Indexed(direction = IndexDirection.ASCENDING)
	private String theme;
	//enables full text search
	//input string will be mathced with this index
	@TextIndexed
	private String name;

	//@transient makes to skip the serializatin process and hence 
	//below filed will not be saved in mongo db
	@Transient
	private String fakeProperty;
	
	private DifficultyLevel difficultyLevel;
	
	private List<ProductReview> productReview;
	
	//cusotm filed name in mongodb
	@Field(value = "shippingInfo")
	private Deliveryinfo deliveryInfo;
	
	//below annotation helps in telling spring to use belopw constructor during
	//serialization and deserialization
	@PersistenceConstructor
	public LegoSet(String theme, String name, DifficultyLevel difficultyLevel, List<ProductReview> productReview,
			Deliveryinfo deliveryInfo) {
		this.theme = theme;
		this.name = name;
		this.difficultyLevel = difficultyLevel;
		this.productReview = productReview;
		this.deliveryInfo = deliveryInfo;
	}

	public LegoSet() {
	}

	@Override
	public String toString() {
		return "LegoSet [id=" + id + ", theme=" + theme + ", name=" + name + ", difficultyLevel=" + difficultyLevel
				+ ", productReview=" + productReview + ", deliveryInfo=" + deliveryInfo + "]";
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public List<ProductReview> getProductReview() {
		return productReview;
	}

	public void setProductReview(List<ProductReview> productReview) {
		this.productReview = productReview;
	}

	public Deliveryinfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(Deliveryinfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	
}
