package kz.meirambekuly.examtrainer.services.impl;

import kz.meirambekuly.examtrainer.entities.User;
import kz.meirambekuly.examtrainer.repositories.RoleRepository;
import kz.meirambekuly.examtrainer.repositories.UserRepository;
import kz.meirambekuly.examtrainer.services.UserService;
import kz.meirambekuly.examtrainer.utils.JwtTokenUtils;
import kz.meirambekuly.examtrainer.utils.ObjectMapper;
import kz.meirambekuly.examtrainer.utils.SecurityUtils;
import kz.meirambekuly.examtrainer.utils.StringUtils;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import kz.meirambekuly.examtrainer.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Sets;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<String> getUserAuthorities(Long userId) {
        if (Objects.nonNull(userId)) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return Sets.set("ROLE_" + user.get().getRole().getName());
            }
        }
        return new HashSet<>();
    }

    @Override
    public ResponseDto<?> register(UserDto dto) {
        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());
        if(optionalUser.isEmpty()){
            User user = User.builder()
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(roleRepository.getById(1L))
                    .build();
            user = userRepository.save(user);
            return  ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(user.getId())
                    .build();
        }
        return  ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.NO_CONTENT.value())
                .errorMessage("User with such username already exists")
                .build();
    }

    @Override
    public ResponseDto<?> getToken() {
        if (SecurityUtils.isAuthenticated()) {
            String token = JwtTokenUtils.generateJwt(SecurityUtils.getCurrentUserLogin(), SecurityUtils.getAuthorities());
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(token)
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .errorMessage("UNAUTHORIZED!")
                .build();
    }

    @Override
    public ResponseDto<?> getLoggedUserInformation() {
        Optional<User> user = userRepository.findByUsername(SecurityUtils.getCurrentUserLogin());
        if (user.isPresent()) {
            UserDto dto = ObjectMapper.convertToUserDto(user.get());
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(dto)
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .errorMessage("UNAUTHORIZED!")
                .build();
    }

    @Transactional
    @Override
    public ResponseDto<?> updateUser(UserDto dto) {
        Optional<User> user = userRepository.findByUsername(SecurityUtils.getCurrentUserLogin());
        if (user.isPresent()) {
            if (!StringUtils.isEmpty(dto.getUsername())) {
                user.get().setUsername(dto.getUsername());
            }
            if (!StringUtils.isEmpty(dto.getFirstName())) {
                user.get().setFirstName(dto.getFirstName());
            }
            if (!StringUtils.isEmpty(dto.getLastName())) {
                user.get().setLastName(dto.getLastName());
            }
            userRepository.save(user.get());
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(user.get().getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .errorMessage("UNAUTHORIZED!")
                .build();
    }

    @Transactional
    @Override
    public ResponseDto changePassword(String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findByUsername(SecurityUtils.getCurrentUserLogin());
        if (user.isPresent()) {
            if (!StringUtils.isAllEmpty(oldPassword, newPassword) && !StringUtils.equals(oldPassword, newPassword)) {
                if (passwordEncoder.encode(oldPassword).matches(user.get().getPassword())) {
                    user.get().setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user.get());
                    return ResponseDto.builder()
                            .isSuccess(true)
                            .httpStatus(HttpStatus.OK.value())
                            .data(user.get().getId())
                            .build();
                }
            }
            return ResponseDto.builder()
                    .isSuccess(false)
                    .httpStatus(HttpStatus.NO_CONTENT.value())
                    .errorMessage("NOT_VALID_FIELDS")
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.FORBIDDEN.value())
                .errorMessage("FORBIDDEN_ACTION")
                .build();
    }
}
