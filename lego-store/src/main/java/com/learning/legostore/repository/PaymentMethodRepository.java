package com.learning.legostore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.legostore.document.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, String>{

}
