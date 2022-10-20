package com.example.phob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ContactCtr {
    private static final Logger log = LogManager.getLogger(ContactCtr.class);

    private ContactRepo repo;

    public ContactCtr(ContactRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public String home(Model model) {
        log.traceEntry("home()");

        model.addAttribute("contacts", repo.findAllByOrderByName());

        return "home";
    }

    @GetMapping("delete")
    public String delete(@RequestParam int id) {
        log.traceEntry("delete({})", id);

        repo.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("create")
    public String create(@RequestParam String name, @RequestParam String phone) {
        log.traceEntry("create({}, {})", name, phone);

        Contact book = repo.save(new Contact(name, phone));
        log.debug("New contact {}", book);

        return "redirect:/";
    }
}
