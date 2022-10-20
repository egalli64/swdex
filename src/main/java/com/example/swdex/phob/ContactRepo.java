package com.example.swdex.phob;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends CrudRepository<Contact, Integer> {
    Iterable<Contact> findAllByOrderByName();
}
