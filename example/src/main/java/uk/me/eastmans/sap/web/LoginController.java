package uk.me.eastmans.sap.web;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by meastman on 23/12/15.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(HttpServletRequest request, @RequestParam("failure") Optional<String> failure, Model model) {
        if (failure.isPresent())
        {
            Exception e = (Exception)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            model.addAttribute("failureReason", e.getMessage());
            if (e instanceof CredentialsExpiredException)
                model.addAttribute("changePassword", Boolean.TRUE);
        }
        model.addAttribute("failure", failure);
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegistration()
    {
        // Process the form and register this person as a user.
        return "register";
    }

}
