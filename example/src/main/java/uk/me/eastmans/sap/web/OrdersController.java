package uk.me.eastmans.sap.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrdersController {

    @Secured({"ROLE_ORDERS"})
    @RequestMapping("/orders")
    public String index() {
        return "orders";
    }

}
