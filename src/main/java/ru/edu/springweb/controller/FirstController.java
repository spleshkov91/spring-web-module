package ru.edu.springweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String authorPage(Model model) {
        model.addAttribute("authorName", "Вячеслав Андреевич Плешков");
        return "author";
    }

    @GetMapping("/hobbies")
    public String hobbiesPage(Model model) {
        model.addAttribute("hobbies", "Хоккей, путешествия, автомобили");
        return "hobbies";
    }
}
