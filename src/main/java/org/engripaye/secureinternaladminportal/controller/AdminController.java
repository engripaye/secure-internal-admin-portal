package org.engripaye.secureinternaladminportal.controller;

import org.engripaye.secureinternaladminportal.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('PERM_DASHBOARD_VIEW')")
    public String adminHome(Model model, @AuthenticationPrincipal OidcUser oidcUser){
        model.addAttribute("user", oidcUser.getAttribute("email"));
        return "admin/home";

    }

    @PostMapping("/user/{id}/disable")
    @PreAuthorize("hasAuthority('PERM_USER_EDIT')")
    public String disableUser(@PathVariable Long id){
        //service call to disable user
        userService.disableUser(id);
        return "redirect:/admin";

    }
}
