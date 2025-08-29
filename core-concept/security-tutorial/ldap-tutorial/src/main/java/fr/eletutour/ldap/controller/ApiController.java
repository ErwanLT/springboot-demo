package fr.eletutour.ldap.controller;

import fr.eletutour.ldap.dto.NewUserDto;
import fr.eletutour.ldap.service.LdapService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (!model.containsAttribute("newUser")) {
            model.addAttribute("newUser", new NewUserDto());
        }
        return "admin/user-list";
    }

    @PreAuthorize("hasRole('ADMINS')")
    @PostMapping("/admin/users/create")
    public String createUser(@Valid @ModelAttribute("newUser") NewUserDto newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUser", bindingResult);
            redirectAttributes.addFlashAttribute("newUser", newUser);
            return "redirect:/admin/users";
        }

        try {
            ldapService.createUser(newUser);
            if (newUser.getRoles() == null || newUser.getRoles().isEmpty()) {
                // By default, add to USERS group if no role is selected
                ldapService.addUserToGroup(newUser.getFullName(), "USERS");
            } else {
                for (String role : newUser.getRoles()) {
                    ldapService.addUserToGroup(newUser.getFullName(), role);
                }
            }
            redirectAttributes.addFlashAttribute("successMessage", "User " + newUser.getFullName() + " created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating user: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }
}