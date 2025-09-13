package com.example.swdex.phob;

import org.springframework.data.repository.CrudRepository;

public interface ContactRepo extends CrudRepository<Contact, Integer> {
    Iterable<Contact> findAllByOrderByName();
}
