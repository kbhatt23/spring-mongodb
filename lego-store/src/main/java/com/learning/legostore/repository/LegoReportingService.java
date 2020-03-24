package com.learning.legostore.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;
//shud not use @repsoitory as that contains exptra excption handling of sql exception -> converting them into runtime exceptio
	//-> also transactions are there by default in @repsoitory

import com.learning.legostore.document.AverageRatingModel;
import com.learning.legostore.document.LegoSet;
@Service
public class LegoReportingService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	//sample query in mongo for data projection
	
	//db.legoSets.aggregate([{
	//	$project:{
	//	legoSetName: "$name",
	//	averageRating: {$avg:"$productReview.rating"}
	//	}
	//	}]);
	
	public List<AverageRatingModel> generateAverageRatingReport(){
		ProjectionOperation projectOperation = Aggregation.project()
					.andExpression("name").as("productName")
					.andExpression("{$avg : '$productReview.rating'}").as("averageRating");
		
		Aggregation averageRatingAggreagation = Aggregation.newAggregation(LegoSet.class , projectOperation);
		
		return mongoTemplate.aggregate(averageRatingAggreagation, LegoSet.class , AverageRatingModel.class).getMappedResults();
	}

}
