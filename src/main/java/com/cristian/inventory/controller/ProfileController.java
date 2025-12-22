package com.cristian.inventory.controller;

import com.cristian.inventory.dto.AuthDto;
import com.cristian.inventory.dto.ProfileDto;
import com.cristian.inventory.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDto> register(@RequestBody ProfileDto profileDto) {
        ProfileDto registeredProfile = profileService.register(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDto authDto) {
        try {
           Map<String, Object> response = profileService.authenticateAndGenerateToken(authDto);
           return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<ProfileDto>> getAllUsers() {
        List<ProfileDto> allUsers = profileService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getPublicProfile() {
        ProfileDto publicProfile = profileService.getPublicProfile(null);
        return ResponseEntity.ok(publicProfile);
    }
}
