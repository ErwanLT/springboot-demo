/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of ldap-tutorial.
 *
 * ldap-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ldap-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ldap-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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

    private final LdapService ldapService;

    public ApiController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

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

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
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