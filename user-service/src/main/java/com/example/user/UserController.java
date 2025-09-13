/*
 * Spring Boot Microservices tutorial - User service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static Logger log = LogManager.getLogger(UserController.class);

    private final UserService svc;

    public UserController(UserService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<User> getAll() {
        log.traceEntry("getAll()");
        return svc.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        log.traceEntry("getById({})", id);
        User user = svc.getById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.traceEntry("create({})", user);
        return svc.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User newer) {
        log.traceEntry("update({}, {})", id, newer);
        User user = svc.getById(id);
        if (user != null) {
            user.setName(newer.getName());
            user.setEmail(newer.getEmail());
            return ResponseEntity.ok(svc.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.traceEntry("delete({})", id);
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
