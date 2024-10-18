package br.com.fiap.javabet.auth;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import br.com.fiap.javabet.user.User;
import br.com.fiap.javabet.user.UserService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private final UserService userService;

    public LoginListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) principal;
            String email = oauthUser.getAttribute("email");
            String name = oauthUser.getAttribute("name");
            userService.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                return userService.save(newUser);
            });
        }
    }

}
