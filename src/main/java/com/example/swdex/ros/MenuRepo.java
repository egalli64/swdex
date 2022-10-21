package com.example.swdex.ros;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends CrudRepository<Menu, Integer> {
}
