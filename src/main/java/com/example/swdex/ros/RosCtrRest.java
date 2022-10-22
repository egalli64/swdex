package com.example.swdex.ros;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RosCtrRest {
    private static final Logger log = LogManager.getLogger(RosCtrRest.class);

    @GetMapping("/ros/increase/{id}")
    public boolean increase(@PathVariable int id, HttpSession session) {
        log.traceEntry("increase({})", id);

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> orders = (Map<Integer, Integer>) session.getAttribute("orders");
        Integer prev = orders.putIfAbsent(id, 1);
        if (prev != null) {
            orders.put(id, prev + 1);
            return true;
        }

        log.trace("Current orders {}", orders);
        return false;
    }
}
