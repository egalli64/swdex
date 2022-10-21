package com.example.swdex.ros;

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

    public RosCtr(CategoryRepo catRepo, MenuRepo menuRepo) {
        this.catRepo = catRepo;
        this.menuRepo = menuRepo;
    }

    @GetMapping
    public String home(Model model) {
        log.traceEntry("home()");

        model.addAttribute("categories", catRepo.findAll());
        model.addAttribute("menuItems", menuRepo.findAll());

        return "/ros/home";
    }
}
