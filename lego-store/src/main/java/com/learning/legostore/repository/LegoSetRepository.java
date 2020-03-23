package com.learning.legostore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.learning.legostore.document.DifficultyLevel;
import com.learning.legostore.document.LegoSet;
@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String>{

	//this method returns list of items with ignore case and filed value in db contains the theme string as substring
	List<LegoSet> findByThemeContainsIgnoreCase(String theme);
	
	//this method return list of items with exact theme match(without ignore case and exact match)
	List<LegoSet> findByTheme(String theme);
	
	//theme in DB should start with theme string input and difficulty level in db should be as mentioned in method inpout
	List<LegoSet> findByThemeStartsWithAndDifficultyLevel(String theme, DifficultyLevel difficulty);
	
	@Query("{'deliveryInfo.deliveryFee' : {$lt : ?0}}")
	List<LegoSet> findItemsWithDeliveryFeeGreaterThanVal(double price);
	
	//this method returns all lego set items with any of the review containing the full rating
	@Query("{'productReview.rating' : {$eq : 10}}")
	List<LegoSet> findAllByPerfectRating();
		
}
