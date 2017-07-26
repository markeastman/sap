package uk.me.eastmans.sap.web.home;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String index(Map<String, Object> model) {
        return "home";
    }

}
