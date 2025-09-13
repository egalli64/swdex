/*
 * Spring Boot Microservices tutorial - User service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static Logger log = LogManager.getLogger(UserService.class);
    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        log.traceEntry("getAll()");
        return repo.findAll();
    }

    public User getById(Long id) {
        log.traceEntry("getById({})", id);
        return repo.findById(id).orElse(null);
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
