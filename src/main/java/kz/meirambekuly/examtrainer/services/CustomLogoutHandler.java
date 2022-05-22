package kz.meirambekuly.examtrainer.services;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final String header = HttpHeaders.AUTHORIZATION;

    private List<String> expiredTokens = new ArrayList<>();

    public List<String> getExpiredTokens() {
        return expiredTokens;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextHolder.clearContext();
        String jwt = request.getHeader(header).replace("Bearer ", "");
        expiredTokens.add(jwt);
    }
}
