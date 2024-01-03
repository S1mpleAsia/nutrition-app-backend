package com.example.nutritionapp.controller;

import com.example.nutritionapp.dto.UserDTO;
import com.example.nutritionapp.http.request.InitAccountRequest;
import com.example.nutritionapp.http.request.UserInfoRequest;
import com.example.nutritionapp.http.response.GeneralListResponse;
import com.example.nutritionapp.http.response.GeneralResponse;
import com.example.nutritionapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/admin")
    public GeneralResponse<UserDTO> createAdminUser(@RequestBody @Valid InitAccountRequest initAccountRequest) {
        return userService.createAdminUser(initAccountRequest);
    }

    @PostMapping("/sign-up")
    public GeneralResponse<UserDTO> initAccount(@RequestBody @Valid InitAccountRequest initAccountRequest) {
        return userService.initAccount(initAccountRequest);
    }

    @PostMapping("/sign-in")
    public GeneralResponse<UserDTO> signIn(@RequestBody @Valid UserDTO userDTO) {
        return userService.signIn(userDTO);
    }

    @PutMapping("/{userId}")
    public GeneralResponse<UserDTO> updateInfo(@PathVariable("userId") UUID userId, @RequestBody UserInfoRequest request) {
        return userService.updateInfo(userId, request);
    }

    @PutMapping("/{userId}/change-pwd")
    public GeneralResponse<UserDTO> changePassword(@PathVariable("userId") UUID userId, @RequestBody String password) {
        return userService.changePassword(userId, password);
    }

    @GetMapping("/{userId}/profile")
    public GeneralResponse<UserDTO> viewProfile(@PathVariable("userId") UUID userId) {
        return userService.viewProfile(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/all-user")
    public GeneralListResponse<UserDTO> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/ban-user")
    public GeneralListResponse<UserDTO> getAllBannedUser() {
        return userService.getAllBannedUser();
    }
}
