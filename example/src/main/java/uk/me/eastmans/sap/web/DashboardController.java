package uk.me.eastmans.sap.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @RequestMapping({"/dashboard", "/home"}) // We can have multiple URIs going to the same place
    public String dashboard() {
        return "dashboard";
    }
}
