/*
 * Spring Boot Microservices tutorial - User service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.user;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static Logger log = LogManager.getLogger(UserService.class);
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        log.traceEntry("getAll()");
        return repo.findAll();
    }

    public Optional<User> get(Long id) {
        log.traceEntry("get({})", id);
        return repo.findById(id);
    }
    
    public User save(User user) {
        log.traceEntry("save({})", user);
        return repo.save(user);
    }

    public void delete(Long id) {
        log.traceEntry("delete({})", id);
        repo.deleteById(id);
    }
}
