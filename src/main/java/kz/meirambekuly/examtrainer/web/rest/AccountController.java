package kz.meirambekuly.examtrainer.web.rest;

import kz.meirambekuly.examtrainer.services.UserService;
import kz.meirambekuly.examtrainer.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;


    @GetMapping("/token")
    public ResponseEntity<?> getToken() {
        return ResponseEntity.ok(userService.getToken());
    }

    @GetMapping("")
    public ResponseEntity<?> account(){
        return ResponseEntity.ok(userService.getLoggedUserInformation());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateUser(dto));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> resetPassword(@RequestParam("oldPassword") String oldPassword,
                                           @RequestParam("newPassword") String newPassword) {
        return ResponseEntity.ok(userService.changePassword(oldPassword, newPassword));
    }

}
