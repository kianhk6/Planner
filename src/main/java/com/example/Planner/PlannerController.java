package com.example.Planner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlannerController {
    public final int MAX_CORNER = 9;
    public final int MIN_CORNER = 0;

    @GetMapping("/about")
    public String getMyName(){
        return "Kian Hosseinkhani & Sasha Vujisic";
    }
}
