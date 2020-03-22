package com.learning.legostore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.legostore.document.LegoSet;
@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String>{

}
