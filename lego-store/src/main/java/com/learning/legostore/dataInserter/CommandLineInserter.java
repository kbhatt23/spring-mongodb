package com.learning.legostore.dataInserter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.learning.legostore.document.Deliveryinfo;
import com.learning.legostore.document.DifficultyLevel;
import com.learning.legostore.document.LegoSet;
import com.learning.legostore.document.PaymentMethod;
import com.learning.legostore.document.PaymentOption;
import com.learning.legostore.document.ProductReview;
import com.learning.legostore.repository.LegoSetRepository;
import com.learning.legostore.repository.PaymentMethodRepository;
//commented below just to test the data migration
//after testing migration we can remove the below comment
@Service
public class CommandLineInserter implements CommandLineRunner{
	
	@Autowired
	private LegoSetRepository legoSetRepository;
	
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Override
	public void run(String... args) throws Exception {

		System.out.println("Starting CommandLineInserter to insert data");
		paymentMethodRepository.deleteAll();
		legoSetRepository.deleteAll();
		
		PaymentMethod paymentMethod = new PaymentMethod("ram bhakt hanuman", 10, PaymentOption.CASH);
		//without creating the referenced document we cannot refer it in main class
		paymentMethodRepository.insert(paymentMethod);
		
		
		List<ProductReview> productReviews1 = IntStream.rangeClosed(1, 5)
				 .boxed()
				 .map(rating -> new ProductReview("user"+rating, rating))
				 .collect(Collectors.toList());
		Deliveryinfo deliveryInfo = new Deliveryinfo(LocalDate.now(), 10.10, true);
		
		LegoSet legoSet1 = new LegoSet("batman", "home toy lego 1", DifficultyLevel.MEDIUM,
				productReviews1, deliveryInfo);
		legoSet1.setPaymentMethod(paymentMethod);
		//insert enforces a new entry comes
		//no option of update
		
		List<ProductReview> productReviews2 = IntStream.rangeClosed(4, 8)
				 .boxed()
				 .map(rating -> new ProductReview("user"+rating*rating, rating))
				 .collect(Collectors.toList());
		Deliveryinfo deliveryInfo2 = new Deliveryinfo(LocalDate.now(), 99.99, true);
		
		LegoSet legoSet2 = new LegoSet("superman", "air bridge lego toy ", DifficultyLevel.HARD,
				productReviews2, deliveryInfo2);
		
		
		List<ProductReview> productReviews3 = IntStream.rangeClosed(5, 10)
				 .boxed()
				 .map(rating -> new ProductReview("user"+rating, rating))
				 .collect(Collectors.toList());
		Deliveryinfo deliveryInfo3 = new Deliveryinfo(LocalDate.now(), 11.11, true);
		
		LegoSet legoSet3 = new LegoSet("batman", "complicated atom bomb controller", DifficultyLevel.NOT_FOUND,
				productReviews3, deliveryInfo3);
		
		List<ProductReview> productReviews4 = IntStream.rangeClosed(6, 10)
				 .boxed()
				 .map(rating -> new ProductReview("user"+rating, rating))
				 .collect(Collectors.toList());
		Deliveryinfo deliveryInfo4 = new Deliveryinfo(LocalDate.now(), 101.10, true);
		
		LegoSet legoSet4 = new LegoSet("batman", "alpha male lego", DifficultyLevel.HARD,
				productReviews4, deliveryInfo4);
		
		
		//legoSetRepository.insert(legoSet1);
		//legoSetRepository.insert(legoSet2);
		//we can use below for batch insert
		legoSetRepository.insert(Arrays.asList(legoSet1,legoSet2,legoSet3 , legoSet4));
	}

}
