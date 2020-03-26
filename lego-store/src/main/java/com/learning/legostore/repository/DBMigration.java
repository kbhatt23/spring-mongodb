package com.learning.legostore.repository;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.learning.legostore.document.LegoSet;

@ChangeLog(order = "001")
public class DBMigration {

	@ChangeSet(order = "001", author = "kbhatt23" , id = "new number migration 1")
	public void updateNEwNumberProperty(MongoTemplate mongoTemplate) {
		 Criteria priceZeroCriteria = new Criteria().orOperator(
	                Criteria.where("newProperty").is(0),
	                Criteria.where("newProperty").is(null));
	        mongoTemplate.updateMulti(
	                new Query(priceZeroCriteria),
	                Update.update("newProperty", 122),
	                LegoSet.class);

	}
}
