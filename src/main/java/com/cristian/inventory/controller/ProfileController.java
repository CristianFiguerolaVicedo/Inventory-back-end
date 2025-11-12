package com.cristian.inventory.controller;

import com.cristian.inventory.dto.ProfileDto;
import com.cristian.inventory.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDto> register(@RequestBody ProfileDto profileDto) {
        ProfileDto registeredProfile = profileService.register(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }
}
