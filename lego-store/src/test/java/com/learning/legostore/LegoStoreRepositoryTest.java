package com.learning.legostore;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.learning.legostore.document.Deliveryinfo;
import com.learning.legostore.document.DifficultyLevel;
import com.learning.legostore.document.LegoSet;
import com.learning.legostore.document.ProductReview;
import com.learning.legostore.repository.LegoSetRepository;

@RunWith(SpringRunner.class)
//allows mongo db repository components to be injected using autowiring
@DataMongoTest
@DirtiesContext
public class LegoStoreRepositoryTest {
	
	@Autowired
	private LegoSetRepository legoSetRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Before
	public void setupData() {
		List<ProductReview> productReviews3 = IntStream.rangeClosed(5, 10)
				 .boxed()
				 .map(rating -> new ProductReview("user"+rating, rating))
				 .collect(Collectors.toList());
		Deliveryinfo deliveryInfo3 = new Deliveryinfo(LocalDate.now(), 11.11, true);
		
		LegoSet legoSet3 = new LegoSet("batman", "complicated atom bomb controller", DifficultyLevel.NOT_FOUND,
				productReviews3, deliveryInfo3);
		

		List<ProductReview> productReviews1 = IntStream.rangeClosed(1, 5)
				 .boxed()
				 .map(rating -> new ProductReview("user"+rating, rating))
				 .collect(Collectors.toList());
		Deliveryinfo deliveryInfo = new Deliveryinfo(LocalDate.now(), 10.10, true);
		
		LegoSet legoSet1 = new LegoSet("batman", "home toy lego 1", DifficultyLevel.MEDIUM,
				productReviews1, deliveryInfo);
		//could have also used mongoTemplate
		mongoTemplate.insert(legoSet1);		
		mongoTemplate.insert(legoSet3);
	}
	
	@Test
	public void testPerfectPRoducts() {
		List<LegoSet> perfectItems = legoSetRepository.findAllByPerfectRating();
		
		assertEquals(1, perfectItems.size());
		assertEquals("complicated atom bomb controller", perfectItems.get(0).getName());
	}

}
