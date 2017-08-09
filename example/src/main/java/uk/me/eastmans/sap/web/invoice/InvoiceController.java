package uk.me.eastmans.sap.web.invoice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InvoiceController {

    @RequestMapping("/invoices/{id}/print")
    public String incoicePrint(@PathVariable("id") String id) {
        return "invoice-print";
    }

    @RequestMapping("/invoices/{id}")
    public String invoice(@PathVariable("id") String id) {
        return "invoice";
    }

}
