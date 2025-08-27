package fr.eletutour.ldap.controller;

import fr.eletutour.ldap.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @Autowired
    private LdapService ldapService;

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("hasRole('ADMINS')")
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", ldapService.findAllUsers());
        return "admin/user-list";
    }
}