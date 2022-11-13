package com.varaprasad.graphqldemo.repo;

import com.varaprasad.graphqldemo.entity.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
