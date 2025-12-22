package com.cristian.inventory.service;

import com.cristian.inventory.dto.AuthDto;
import com.cristian.inventory.dto.ProfileDto;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.exceptions.EmailAlreadyExistsException;
import com.cristian.inventory.repository.ProfileRepository;
import com.cristian.inventory.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ProfileDto register(ProfileDto profileDto) {
        if (profileRepository.findByEmail(profileDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("This email is already registered");
        }

        ProfileEntity newProfile = toEntity(profileDto);
        newProfile = profileRepository.save(newProfile);
        return toDto(newProfile);
    }

    public ProfileEntity getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return profileRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Profile with email " + authentication.getName() + " not found"));
    }

    public List<ProfileDto> getAllUsers() {
        return profileRepository.findAll().stream().map(this::toDto).toList();
    }

    public ProfileDto getPublicProfile(String email) {
        ProfileEntity currentUser = null;
        if (email == null) {
            currentUser = getCurrentProfile();
        } else {
            currentUser = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Profile with email " + email + " not found"));
        }

        return ProfileDto.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .profileImageUrl(currentUser.getProfileImageUrl())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }

    public Map<String, Object> authenticateAndGenerateToken(AuthDto authDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
            String token = jwtUtil.generateToken(authDto.getEmail());
            return Map.of(
                    "token", token,
                    "userProfile", getPublicProfile(authDto.getEmail())
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    //HELPER METHODS

    public ProfileEntity toEntity(ProfileDto profileDto) {
        return ProfileEntity.builder()
                .id(profileDto.getId())
                .name(profileDto.getName())
                .email(profileDto.getEmail())
                .password(passwordEncoder.encode(profileDto.getPassword()))
                .profileImageUrl(profileDto.getProfileImageUrl())
                .createdAt(profileDto.getCreatedAt())
                .updatedAt(profileDto.getUpdatedAt())
                .build();
    }

    public ProfileDto toDto(ProfileEntity profileEntity) {
        return ProfileDto.builder()
                .id(profileEntity.getId())
                .name(profileEntity.getName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }
}
