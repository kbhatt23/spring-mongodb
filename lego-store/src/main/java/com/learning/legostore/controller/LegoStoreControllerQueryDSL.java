package com.learning.legostore.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.legostore.document.DifficultyLevel;
import com.learning.legostore.document.LegoSet;
import com.learning.legostore.document.QLegoSet;
import com.learning.legostore.repository.LegoSetRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@RestController
@RequestMapping("/v2/legoStore/legoItems")
public class LegoStoreControllerQueryDSL {
	
	@Autowired
	private LegoSetRepository respository;
	//sort by name
	private Sort sortAscending = Sort.by("name").ascending();
	
	//filter added so taht it can be reused at other places
	//we can move this to constatn file later
	
	QLegoSet qLegoSet = new QLegoSet("legoSet");
	//filter for in stock items
	BooleanExpression instockITem  = qLegoSet.deliveryInfo.instock.isTrue();
	
	//filter for price
	BooleanExpression filterPRice = qLegoSet.deliveryInfo.deliveryFee.lt(11.12D);
	
	//filter for good rating
	BooleanExpression filterGoodRating = qLegoSet.productReview.any().rating.eq(10);
	
	//filter for all review to be empty
	BooleanExpression filterEmptyRating = qLegoSet.productReview.isEmpty();
	
	BooleanExpression filterOutOfStock = qLegoSet.deliveryInfo.instock.isFalse();
	
	@GetMapping
	public ResponseEntity<List<LegoSet>> findAll(){
		
		//List<LegoSet> legoSets = respository.findAll();
		
		Iterator<LegoSet> itemsIterator = respository.findAll(instockITem.and(filterPRice).and(filterGoodRating)).iterator();
		List<LegoSet> legoSets = new ArrayList<LegoSet>();
		while(itemsIterator.hasNext()) {
			legoSets.add(itemsIterator.next());
		}
		
		if(legoSets == null || legoSets.isEmpty()) {
			//or else we cna throw custom excpetion
			//and handle this in @exceptionhandler of @controller advice
			//throw new RuntimeException("Empty Lego Sets");
			//as of now we are returning appropriate httpstatus
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<LegoSet>>(legoSets, HttpStatus.OK);
	}
	
	//returns list of lego tiems that are either out of stock or have no reviews by users
	@GetMapping("/badItems")
	public ResponseEntity<List<LegoSet>> findAllBadItems(){
	
		//List<LegoSet> legoSets = respository.findAll();
		
		Iterator<LegoSet> itemsIterator = respository.findAll(filterEmptyRating.or(filterOutOfStock)).iterator();
		List<LegoSet> legoSets = new ArrayList<LegoSet>();
		while(itemsIterator.hasNext()) {
			legoSets.add(itemsIterator.next());
		}
		
		if(legoSets == null || legoSets.isEmpty()) {
			//or else we cna throw custom excpetion
			//and handle this in @exceptionhandler of @controller advice
			//throw new RuntimeException("Empty Lego Sets");
			//as of now we are returning appropriate httpstatus
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<LegoSet>>(legoSets, HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/{legoID}")
	public ResponseEntity<LegoSet> findById(@PathVariable("legoID") String id){
		if(StringUtils.isEmpty(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return respository.findById(id)
		.map(legoItem -> new ResponseEntity<>(legoItem, HttpStatus.OK))
		.orElseThrow(() -> new RuntimeException("lego item not found with id "+id))
		;
	}
	
	@PostMapping
	public ResponseEntity<LegoSet> createNew(@RequestBody LegoSet legoSet){
		if(legoSet.getId() != null) {
			//not using exception handling
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LegoSet createdEntity = respository.insert(legoSet);
		return new ResponseEntity<LegoSet>(createdEntity, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<LegoSet> updateLegoSet(@RequestBody LegoSet legoSet){
		if(StringUtils.isEmpty(legoSet.getId())) {
			//not using exception handling
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LegoSet updatedEntity = respository.save(legoSet);
		return new ResponseEntity<LegoSet>(updatedEntity, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteByID(@PathVariable String id){
		LegoSet itemFound  = respository.findById(id)
		            .orElseThrow(() -> new RuntimeException("lego do not exist to be deleted with ID "+id))
					;
		respository.delete(itemFound);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/themeExact/{themeStr}")
	public ResponseEntity<List<LegoSet>> findByThemeExactMatch(@PathVariable("themeStr") String theme){
		
		List<LegoSet> fetchedItems = respository.findByTheme(theme , sortAscending);
		if(fetchedItems == null || fetchedItems.isEmpty()) {
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
		
	}
	
	@GetMapping("/themeExactSorted/{themeStr}")
	public ResponseEntity<List<LegoSet>> findByThemeExactMatchSorted(@PathVariable("themeStr") String theme){
		
		List<LegoSet> fetchedItems = respository.findByThemeOrderByName(theme);
		if(fetchedItems == null || fetchedItems.isEmpty()) {
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
		
	}
	
	@GetMapping("/theme/{themeStr}")
	public ResponseEntity<List<LegoSet>> findByThemeContaining(@PathVariable("themeStr") String theme){
		
		List<LegoSet> fetchedItems = respository.findByThemeContainsIgnoreCase(theme);
		if(fetchedItems == null || fetchedItems.isEmpty()) {
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
		
	}
	
	@GetMapping("/themeStarts/{themeStr}/difficultyLevel/{difficultyLevel}")
	public ResponseEntity<List<LegoSet>> findByThemeStartsAndDifficulty(@PathVariable("themeStr") String theme , 
			@PathVariable("difficultyLevel") DifficultyLevel difficultyLevel
			){
		
		List<LegoSet> fetchedItems = respository.findByThemeStartsWithAndDifficultyLevel(theme, difficultyLevel);
		if(fetchedItems == null || fetchedItems.isEmpty()) {
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
		
	}
	
	@GetMapping("/priceLessThan/{price}")
	public ResponseEntity<List<LegoSet>> findByPriceLessThan(@PathVariable("price") double price){
		
		List<LegoSet> fetchedItems = respository.findItemsWithDeliveryFeeGreaterThanVal(price);
		if(fetchedItems == null || fetchedItems.isEmpty()) {
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
		
	}
	//return lego items with any one of the review be perfect (10/10)
	@GetMapping("/perfectRating")
	public ResponseEntity<List<LegoSet>> findByPerfectRating(){
		
		List<LegoSet> fetchedItems = respository.findAllByPerfectRating();
		if(fetchedItems == null || fetchedItems.isEmpty()) {
			return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
		
	}
	//find all items where theme is other than
		@GetMapping("/otherThanTheme/{theme}")
		public ResponseEntity<List<LegoSet>> findAllByThemeOterThan(@PathVariable String theme){
			
			List<LegoSet> fetchedItems = respository.findByThemeNot(theme);
			if(fetchedItems == null || fetchedItems.isEmpty()) {
				return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
			
		}
		
		//find all items which are in stock
				@GetMapping("/inStockItems/{stockLevel}")
				public ResponseEntity<List<LegoSet>> findAllInstockItems(@PathVariable boolean stockLevel){
					
					List<LegoSet> fetchedItems = respository.findAllByInstock(stockLevel);
					if(fetchedItems == null || fetchedItems.isEmpty()) {
						return new ResponseEntity<List<LegoSet>>(HttpStatus.NOT_FOUND);
					}
					
					return new ResponseEntity<List<LegoSet>>(fetchedItems, HttpStatus.OK);
					
				}
	
	
}
