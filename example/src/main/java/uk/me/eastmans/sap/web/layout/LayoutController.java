package uk.me.eastmans.sap.web.layout;

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
public class LayoutController {

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
        return "redirect:/dashboard";
    }
}
