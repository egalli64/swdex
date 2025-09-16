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

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/users
     * </pre>
     */
    @GetMapping
    public List<User> getAll() {
        log.traceEntry("getAll()");
        return svc.getAll();
    }

    /**
     * <pre>
        curl -X GET http://localhost:8080/api/users/1
     * </pre>
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        log.traceEntry("get({})", id);

        Optional<User> user = svc.get(id);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    /**
     * <pre>
        curl -X POST http://localhost:8080/api/users ^
        -H "Content-Type: application/json" ^
        -d "{\"name\": \"Tom\", \"email\": \"tom@example.com\"}"
     * </pre>
     */
    @PostMapping
    public User create(@RequestBody User user) {
        log.traceEntry("create({})", user);
        return svc.save(user);
    }

    /**
     * <pre>
        curl -X PUT http://localhost:8080/api/users/1 ^
        -H "Content-Type: application/json" ^
        -d "{\"name\": \"Jane Smith\", \"email\": \"jane.smith@example.com\"}"
     * </pre>
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User newer) {
        log.traceEntry("update({}, {})", id, newer);

        Optional<User> opt = svc.get(id);
        if (opt.isPresent()) {
            User user = opt.get();
            user.setName(newer.getName());
            user.setEmail(newer.getEmail());
            return ResponseEntity.ok(svc.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * <pre>
        curl -X DELETE http://localhost:8080/api/users/1
     * </pre>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.traceEntry("delete({})", id);
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
