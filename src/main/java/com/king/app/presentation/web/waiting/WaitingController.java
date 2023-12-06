package com.king.app.presentation.web.waiting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/waiting")
public class WaitingController {
    @GetMapping("/login")
    public String waiting() {
        return "waiting/waiting-login";
    }
    @GetMapping("")
    public String mainView() {
        return "waiting/waiting-view";
    }
}
