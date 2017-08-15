package uk.me.eastmans.sap.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InvoiceController {

    @Secured("ROLE_ORDERS")
    @RequestMapping("/invoices/{id}/print")
    public String incoicePrint(@PathVariable("id") String id) {
        return "invoice-print";
    }

    @Secured("ROLE_ORDERS")
    @RequestMapping("/invoices/{id}")
    public String invoice(@PathVariable("id") String id) {
        return "invoice";
    }

}
