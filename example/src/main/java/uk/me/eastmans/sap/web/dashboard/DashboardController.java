package uk.me.eastmans.sap.web.dashboard;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.me.eastmans.sap.b1.SapCompany;
import uk.me.eastmans.sap.b1.SapUser;
import uk.me.eastmans.sap.services.CurrentUser;

import java.util.Set;

@Controller
public class DashboardController {

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
