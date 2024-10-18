package br.com.fiap.javabet.user;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(OAuth2User principal) {
        if (userRepository.findByEmail(principal.getAttribute("email")).isEmpty()) {
            return userRepository.save(new User(principal));
        }
        return null;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        return userRepository.findByEmail(email).orElseGet(
            () -> {
                var user = new User(oauth2User);
                return userRepository.save(user);
            }
        );
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }


}
