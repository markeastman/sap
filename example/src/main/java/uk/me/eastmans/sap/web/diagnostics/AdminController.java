package uk.me.eastmans.sap.web.diagnostics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String diagnostics() {
        return "admin";
    }
}
