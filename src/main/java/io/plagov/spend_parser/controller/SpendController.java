package io.plagov.spend_parser.controller;

import io.plagov.spend_parser.service.SpendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class SpendController {

    private final SpendService spendService;

    public SpendController(SpendService spendService) {
        this.spendService = spendService;
    }

    @GetMapping("/api/spends")
    public String getSpends() throws IOException {
        spendService.calculateSpends();
        return "total";
    }
}
