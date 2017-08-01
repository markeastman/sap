package uk.me.eastmans.sap.web.home;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value="/changeCompany/{newCompanyId}", method = RequestMethod.GET)
    public String changeCompany(@PathVariable("newCompanyId") String companyId, Authentication auth) {
        if (auth.isAuthenticated())
        {
            CurrentUser user = (CurrentUser) auth.getPrincipal();
            // Ensure we are in a different company
            if (!user.getActiveCompany().getName().equals(companyId))
            {
                // Check to see if we have access to the company
                SapUser sapUser = user.getSapUser();
                Set<SapCompany> companies = sapUser.getAllowedCompanies();
                for (SapCompany cmpy : companies)
                {
                    if (cmpy.getId().equals(companyId))
                    {
                        // We have found the match
                        user.setActiveCompany(cmpy);
                        break;
                    }
                }
            }
        }
        return "redirect:/home";
    }
}
