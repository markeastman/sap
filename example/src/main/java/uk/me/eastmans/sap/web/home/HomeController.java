package uk.me.eastmans.sap.web.home;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.me.eastmans.sap.b1.SapCompany;
import uk.me.eastmans.sap.b1.SapUser;
import uk.me.eastmans.sap.services.CurrentUser;

import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String index() {
        System.out.println("home controller is being called");
        return "home";
    }

    @RequestMapping(value="/changeCompany", method = RequestMethod.POST)
    public String changeCompany(@RequestParam String company, Authentication auth) {
        if (auth.isAuthenticated())
        {
            CurrentUser user = (CurrentUser) auth.getPrincipal();
            // Ensure we are in a different company
            if (!user.getActiveCompany().getName().equals(company))
            {
                // Check to see if we have access to the company
                SapUser sapUser = user.getSapUser();
                Set<SapCompany> companies = sapUser.getAllowedCompanies();
                for (SapCompany cmpy : companies)
                {
                    if (cmpy.getName().equals(company))
                    {
                        // We have found the match
                        user.setActiveCompany(cmpy);
                        break;
                    }
                }
            }
        }
        System.out.println( "Changing company to " + company );
        return "home";
    }
}
