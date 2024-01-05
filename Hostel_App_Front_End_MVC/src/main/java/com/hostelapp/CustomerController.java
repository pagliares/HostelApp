package com.hostelapp;

import com.hostelapp.domain.Customer;
import com.hostelapp.domain.CustomerModel;
import com.hostelapp.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rodrigo Martins Pagliares.
 */
@Controller
@RequestMapping("/")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        super();
        this.customerService = customerService;
    }

    @GetMapping(value={"/", "/index"})
    public String getHomePage(Model model){
        return "index";
    }

    @GetMapping(value="/login")
    public String getLoginPage(Model model){
        return "login";
    }

    @GetMapping(value="/logout-success")
    public String getLogoutPage(Model model){
        return "logout";
    }

    @GetMapping(value="/customers")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getCustomers(Model model){
        List<Customer> customers = this.customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers-view";
    }

    @GetMapping(value="/customers/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddCustomerForm(Model model){
        return "customer-view";
    }

    @PostMapping(value="/customers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCustomer(HttpServletRequest request, Model model, @ModelAttribute CustomerModel customerModel){
        Customer customer = this.customerService.addCustomer(customerModel);
        model.addAttribute("customer", customer);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/customers/" + customer.getId());
    }

    @GetMapping(value="/customers/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getCustomer(Model model, @PathVariable long id){
        Customer customer = this.customerService.getCustomer(id);
        model.addAttribute("customer", customer);
        return "customer-view";
    }

    @PostMapping(value="/customers/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateCustomer(Model model, @PathVariable long id, @ModelAttribute CustomerModel customerModel){
        Customer customer = this.customerService.updateCustomer(id, customerModel);
        model.addAttribute("customer", customer);
        model.addAttribute("customerModel", new CustomerModel());
        return "customer-view";
    }

    @GetMapping(value="/contact")
    public String getContactPage(Model model){
        return "contact";
    }

    @GetMapping(value="/technologies")
    public String getTechnologiesPage(Model model){
        return "technologies";
    }

    @GetMapping(value="/park")
    public String getParkPage(Model model){
        return "park";
    }
}
