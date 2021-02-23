package org.neustupov.springboot2mongo.repository;

import org.neustupov.springboot2mongo.domain.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepo extends MongoRepository<ToDo, String> {

}
