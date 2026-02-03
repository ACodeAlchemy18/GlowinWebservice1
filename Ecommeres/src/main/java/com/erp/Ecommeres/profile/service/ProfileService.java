package com.erp.Ecommeres.profile.service;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.profile.dto.ProfileDTO;
import com.erp.Ecommeres.profile.entity.Profile;
import com.erp.Ecommeres.profile.repo.ProfileRepo;
import com.erp.Ecommeres.repo.UserRepo;

@Service
public class ProfileService {

    // 🔹 Profile table repo
    private final ProfileRepo profileRepo;

    // 🔹 User table repo
    private final UserRepo userRepo;

    // 🔥 CONSTRUCTOR (Spring auto injects both)
    public ProfileService(ProfileRepo profileRepo, UserRepo userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
    }

    // ================= FETCH PROFILE =================
    public ProfileDTO getProfileByUserId(Long userId) {

        // 🔵 Get USER (must exist)
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found for userId=" + userId));

        // 🟡 Get PROFILE (optional)
        Profile profile = profileRepo.findByUserId(userId)
                .orElse(null);

        ProfileDTO dto = new ProfileDTO();

        // USER fields
        dto.setUserId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());

        // PROFILE fields (only if exists)
        if (profile != null) {
            dto.setImageUrl(profile.getImageUrl());
            dto.setAge(profile.getAge());
            dto.setGender(profile.getGender());
        }

        return dto;
    }


    // ================= SAVE / UPDATE PROFILE =================
    public Profile saveOrUpdateProfile(Profile profileRequest) {

        return profileRepo.findByUserId(profileRequest.getUserId())
                .map(existingProfile -> {

                    existingProfile.setAge(profileRequest.getAge());
                    existingProfile.setGender(profileRequest.getGender());
                    existingProfile.setImageUrl(profileRequest.getImageUrl());

                    return profileRepo.save(existingProfile);

                })
                .orElseGet(() -> profileRepo.save(profileRequest));
    }
}