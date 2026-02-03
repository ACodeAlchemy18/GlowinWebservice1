package com.erp.Ecommeres.profile.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.Ecommeres.profile.dto.ProfileDTO;
import com.erp.Ecommeres.profile.entity.Profile;
import com.erp.Ecommeres.profile.service.ProfileService;

@RestController
@RequestMapping("/api/profile") // ✅ prefix for consistency
@CrossOrigin("*")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // CREATE or UPDATE profile
    @PostMapping("/save") // ✅ now matches frontend POST /api/profile/save
    public ResponseEntity<?> saveProfile(@RequestBody Profile profile) {
        return ResponseEntity.ok(profileService.saveOrUpdateProfile(profile));
    }

    // GET profile by userId
    @GetMapping("/{userId}") // ✅ matches frontend GET /api/profile/{userId}
    public ProfileDTO getProfile(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }
}