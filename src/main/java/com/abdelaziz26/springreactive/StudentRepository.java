package com.abdelaziz26.springreactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {
    Flux<Student> findByFirstname(String firstname);
}
