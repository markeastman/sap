package uk.me.eastmans.sap.web.orders;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrdersController {

    @RequestMapping("/newOrders")
    public String index() {
        return "newOrders";
    }

}
