package com.example.r2dbctutorial.repository;

import com.example.r2dbctutorial.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {
}
