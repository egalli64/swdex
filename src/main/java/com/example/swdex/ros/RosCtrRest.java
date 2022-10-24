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
        change(ord, 1, order.getPrice());
        log.trace("Current ordering {}", ord);

        return order;
    }

    @GetMapping("/ros/decrease/{id}")
    public Menu decrease(@PathVariable Integer id, HttpSession session) {
        log.traceEntry("decrease({})", id);

        @SuppressWarnings("unchecked")
        Map<Integer, Menu> orders = (Map<Integer, Menu>) session.getAttribute("orders");
        Menu order = orders.get(id);
        Menu copy = new Menu(order);
        order.changeQuantity(-1);

        Ordering ord = (Ordering) session.getAttribute("ordering");
        change(ord, -1, order.getPrice());

        log.trace("Reset {}", order);
        return copy;
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
        change(ord, -copy.getQuantity(), order.getPrice());

        log.trace("Reset {}", order);
        return copy;
    }

    private void change(Ordering ord, int quantity, double price) {
        ord.changeCounter(quantity);
        ord.changeTotal(quantity * price);
    }
}
