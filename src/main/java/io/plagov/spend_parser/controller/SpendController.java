package io.plagov.spend_parser.controller;

import io.plagov.spend_parser.service.SpendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class SpendController {

    private final SpendService spendService;

    public SpendController(SpendService spendService) {
        this.spendService = spendService;
    }

    @GetMapping
    public ModelAndView getIndexPage() {
        return new ModelAndView("index");
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (!file.isEmpty()) {
            var totalSpend = spendService.calculateSpends(file);
            model.addAttribute("totalSpend", totalSpend);
            return "total";
        } else {
            model.addAttribute("error", "Something went wrong");
            return "index";
        }
    }
}
