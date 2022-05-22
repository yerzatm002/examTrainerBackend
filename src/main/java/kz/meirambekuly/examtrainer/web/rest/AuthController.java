package kz.meirambekuly.examtrainer.web.rest;

import kz.meirambekuly.examtrainer.services.UserService;
import kz.meirambekuly.examtrainer.utils.JwtTokenUtils;
import kz.meirambekuly.examtrainer.utils.StringUtils;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import kz.meirambekuly.examtrainer.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<?> register(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    /**
     * Вход в систему
     *
     * @param username -  username
     * @param password - user password
     * @return jwt token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        if (StringUtils.isAnyEmpty(username, password)) {
            throw new RuntimeException();
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            String token = JwtTokenUtils.generateJwt(authentication.getName(), authentication.getAuthorities());
            return ResponseEntity.ok(ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(token)
                    .build());
        }
        return ResponseEntity.ok(ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("Username or password is incorrect")
                .build());
    }
}
