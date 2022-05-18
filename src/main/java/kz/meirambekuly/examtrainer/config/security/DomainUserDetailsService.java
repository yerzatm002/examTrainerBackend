package kz.meirambekuly.examtrainer.config.security;

import kz.meirambekuly.examtrainer.entities.User;
import kz.meirambekuly.examtrainer.services.UserService;
import kz.meirambekuly.examtrainer.utils.ObjectMapper;
import kz.meirambekuly.examtrainer.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UserService userCommonService;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<User> userFromDatabase = userCommonService.findByUsername(username);
        if (userFromDatabase.isEmpty()) {
            throw new RuntimeException();
        }
        UserDto dto = ObjectMapper.convertToUserDto(userFromDatabase.get());
        String password = request.getParameter("password");

        if (!passwordEncoder.matches(password, dto.getPassword())) {
            throw new RuntimeException();
        }

        List<GrantedAuthority> grantedAuthorities = userCommonService.getUserAuthorities(dto.getId())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        return new org.springframework.security.core.userdetails.User(username,
                dto.getPassword(),
                grantedAuthorities);
    }
}
