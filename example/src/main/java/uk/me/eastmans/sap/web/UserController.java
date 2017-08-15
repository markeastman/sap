package uk.me.eastmans.sap.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Secured("ROLE_USERS")
    @RequestMapping("/users/{id}")
    public String userProfile(@PathVariable("id") String id) {
        return "user";
    }

}
