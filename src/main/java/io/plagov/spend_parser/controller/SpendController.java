package io.plagov.spend_parser.controller;

import io.plagov.spend_parser.service.SpendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/api/spends")
    public String getSpends() throws IOException {
        var totalSpend = spendService.calculateSpends();
        return "total";
    }
}
