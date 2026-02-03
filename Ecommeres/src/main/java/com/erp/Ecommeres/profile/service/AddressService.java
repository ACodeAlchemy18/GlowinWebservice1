package com.erp.Ecommeres.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.profile.dto.AddressResponseDTO;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.repo.AddressRepo;
import com.erp.Ecommeres.repo.UserRepo;

@Service
public class AddressService {

    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AddressService(AddressRepo addressRepo, UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    // SAVE
    public AddressResponseDTO saveAndFetchAddress(Long userId, Address address) {

        address.setUserId(userId);
        Address saved = addressRepo.save(address);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setFullName(user.getFullName());
        dto.setMobileNumber(user.getMobileNumber());

        dto.setAddressLine(saved.getAddressLine());
        dto.setCity(saved.getCity());
        dto.setState(saved.getState());
        dto.setPincode(saved.getPincode());

        return dto;
    }

    // GET
    public List<Address> getAddresses(Long userId) {
        return addressRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // DELETE
    public void deleteAddress(Long id) {
        addressRepo.deleteById(id);
    }

    // UPDATE
    public AddressResponseDTO updateAddress(Long id, Address updated) {

        Address existing = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        existing.setAddressLine(updated.getAddressLine());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setPincode(updated.getPincode());

        Address saved = addressRepo.save(existing);

        User user = userRepo.findById(existing.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setFullName(user.getFullName());
        dto.setMobileNumber(user.getMobileNumber());

        dto.setAddressLine(saved.getAddressLine());
        dto.setCity(saved.getCity());
        dto.setState(saved.getState());
        dto.setPincode(saved.getPincode());

        return dto;
    }
}
