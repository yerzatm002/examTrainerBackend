package kz.meirambekuly.examtrainer.utils;

import kz.meirambekuly.examtrainer.entities.Role;
import kz.meirambekuly.examtrainer.entities.User;
import kz.meirambekuly.examtrainer.web.dto.RoleDto;
import kz.meirambekuly.examtrainer.web.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapper {
    public static UserDto convertToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .roleDto(convertToRoleDto(user.getRole()))
                .build();
    }

    public static RoleDto convertToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

}
