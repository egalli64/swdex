package com.example.phob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BookCtr {
    private static final Logger log = LogManager.getLogger(BookCtr.class);

    private BookRepo repo;

    public BookCtr(BookRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String home(Model model) {
        log.traceEntry("home");

        model.addAttribute("phones", repo.findAll());

        return "home";
    }
}
