package uk.me.eastmans.sap.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/users/{id}")
    public String userProfile(@PathVariable("id") String id) {
        return "user";
    }

}
