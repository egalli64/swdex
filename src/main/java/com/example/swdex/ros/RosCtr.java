package com.example.swdex.ros;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ros")
public class RosCtr {
    private static final Logger log = LogManager.getLogger(RosCtr.class);

    private CategoryRepo catRepo;
    private MenuRepo menuRepo;
    private OrderingRepo ordRepo;

    public RosCtr(CategoryRepo catRepo, MenuRepo menuRepo, OrderingRepo ordRepo) {
        this.catRepo = catRepo;
        this.menuRepo = menuRepo;
        this.ordRepo = ordRepo;
    }

    @GetMapping
    public String home(HttpSession session) {
        log.traceEntry("home()");

        if (session.getAttribute("ordering") == null) {
            Ordering ord = ordRepo.save(new Ordering());
            session.setAttribute("ordering", ord);
            session.setAttribute("orders", new HashMap<Integer, Integer>());

            session.setAttribute("categories", catRepo.findAll());
            session.setAttribute("menuItems", menuRepo.findAll());
        }

        return "/ros/home";
    }

    @GetMapping("cart")
    public String cart(HttpSession session) {
        log.traceEntry("cart()");

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> orders = (Map<Integer, Integer>) session.getAttribute("orders");
        @SuppressWarnings("unchecked")
        Iterable<Menu> items = (Iterable<Menu>) session.getAttribute("menuItems");
        Ordering ord = (Ordering) session.getAttribute("ordering");

        int counter = 0;
        ord.setTotal(0.0);
        for (Menu item : items) {
            Integer cur = orders.get(item.getId());
            if (cur != null) {
                item.setQuantity(cur);
                counter += cur;
                ord.setTotal(ord.getTotal() + cur * item.getPrice());
            } else {
                item.setQuantity(0);
            }
        }
        session.setAttribute("counter", counter);

        return "/ros/cart";
    }

    @GetMapping("checkout")
    public String checkout(HttpSession session) {
        log.traceEntry("checkout()");

        session.invalidate();

        return "redirect:/";
    }
}
