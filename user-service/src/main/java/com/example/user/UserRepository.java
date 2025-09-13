/*
 * Spring Boot Microservices tutorial - User service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}