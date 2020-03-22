package com.learning.legostore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.learning.legostore.document.LegoSet;
import com.learning.legostore.repository.LegoSetRepository;

@RestController
@RequestMapping("/v1/legoStore/legoItems")
public class LegoStoreController {
	
	@Autowired
	private LegoSetRepository respository;

	@GetMapping
	public ResponseEntity<List<LegoSet>> findAll(){
		
		List<LegoSet> legoSets = respository.findAll();
		
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
		if(!StringUtils.isEmpty(legoSet.getId())) {
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
}