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
    public Menu increase(@PathVariable Integer id, HttpSession session) {
        log.traceEntry("increase({})", id);

        @SuppressWarnings("unchecked")
        Map<Integer, Menu> orders = (Map<Integer, Menu>) session.getAttribute("orders");
        Menu order = orders.get(id);
        order.changeQuantity(1);
        log.trace("Current order {}", order);

        Ordering ord = (Ordering) session.getAttribute("ordering");
        ord.changeCounter(1);
        ord.changeTotal(order.getPrice());
        log.trace("Current ordering {}", ord);

        return order;
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
    public Menu reset(@PathVariable Integer id, HttpSession session) {
        log.traceEntry("reset({})", id);

        @SuppressWarnings("unchecked")
        Map<Integer, Menu> orders = (Map<Integer, Menu>) session.getAttribute("orders");
        Menu order = orders.get(id);
        Menu copy = new Menu(order);
        order.setQuantity(0);

        Ordering ord = (Ordering) session.getAttribute("ordering");
        int less = -copy.getQuantity();
        ord.changeCounter(less);
        ord.changeTotal(less * order.getPrice());

        log.trace("Reset {}", order);
        return copy;
    }
}
