package kz.meirambekuly.examtrainer.services;

import kz.meirambekuly.examtrainer.entities.User;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import kz.meirambekuly.examtrainer.web.dto.UserDto;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> findByUsername(String username);

    Set<String> getUserAuthorities(Long userId);

    ResponseDto register(UserDto dto);

    ResponseDto getToken();

    ResponseDto getLoggedUserInformation();

    ResponseDto updateUser(UserDto dto);

    ResponseDto changePassword(String oldPassword, String newPassword);
}
