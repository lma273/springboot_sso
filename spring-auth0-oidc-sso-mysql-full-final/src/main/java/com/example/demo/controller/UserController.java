package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final OAuth2AuthorizedClientService clientService;

    public UserController(UserService userService, OAuth2AuthorizedClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            String email = oidcUser.getEmail();
            String name = oidcUser.getFullName();
            String auth0Id = oidcUser.getSubject();
            User user = userService.saveOrUpdate(email, name, auth0Id);
            model.addAttribute("user", user);
            model.addAttribute("claims", oidcUser.getClaims());
        }
        return "profile";
    }

    @GetMapping("/tokens")
    public String tokens(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient("auth0", oidcUser.getName());
        if (client != null) {
            String accessToken = client.getAccessToken().getTokenValue();
            String refreshToken = client.getRefreshToken() != null
                    ? client.getRefreshToken().getTokenValue()
                    : "No refresh token";
            model.addAttribute("accessToken", accessToken);
            model.addAttribute("refreshToken", refreshToken);
        }
        return "tokens";
    }

    // Giữ method logout-success duy nhất
    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout"; // trả về logout.html
    }
}

