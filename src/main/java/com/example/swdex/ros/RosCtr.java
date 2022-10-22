package com.example.swdex.ros;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String home(Model model, HttpSession session) {
        log.traceEntry("home()");

        if (session.getAttribute("ordering") == null) {
            Ordering ord = ordRepo.save(new Ordering());
            session.setAttribute("ordering", ord);
            session.setAttribute("orders", new HashMap<Integer, Integer>());
        }

        model.addAttribute("categories", catRepo.findAll());
        model.addAttribute("menuItems", menuRepo.findAll());

        return "/ros/home";
    }
}
