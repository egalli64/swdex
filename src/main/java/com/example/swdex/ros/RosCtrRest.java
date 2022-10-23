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
    public int[] increase(@PathVariable Integer id, HttpSession session) {
        log.traceEntry("increase({})", id);

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> orders = (Map<Integer, Integer>) session.getAttribute("orders");
        Integer prev = orders.putIfAbsent(id, 1);
        if (prev != null) {
            Integer cur = prev + 1;
            orders.put(id, cur);
            log.trace("increased order {}, {}", id, cur);
            return new int[] { id, cur };
        }

        log.trace("Current orders {}", orders);
        return new int[] { id, 1 };
    }

    @GetMapping("/ros/decrease/{id}")
    public int[] decrease(@PathVariable Integer id, HttpSession session) {
        log.traceEntry("decrease({})", id);

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> orders = (Map<Integer, Integer>) session.getAttribute("orders");
        Integer prev = orders.get(id);
        if (prev != null && prev > 0) {
            Integer cur = prev - 1;
            orders.put(id, cur);
            return new int[] { id, cur };
        }

        log.warn("Can't find order {}", id);
        return new int[] { id, 0 };
    }

    @GetMapping("/ros/reset/{id}")
    public int[] reset(@PathVariable Integer id, HttpSession session) {
        log.traceEntry("reset({})", id);

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> orders = (Map<Integer, Integer>) session.getAttribute("orders");
        orders.put(id, 0);

        log.trace("Current orders {}", orders);
        return new int[] { id, 0 };
    }
}
