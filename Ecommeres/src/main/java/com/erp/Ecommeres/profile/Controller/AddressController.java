package com.erp.Ecommeres.profile.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.profile.dto.AddressResponseDTO;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.service.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin("*")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // SAVE
    @PostMapping("/add")
    public AddressResponseDTO saveAddress(
            @RequestHeader("userId") Long userId,
            @RequestBody Address address) {

        return addressService.saveAndFetchAddress(userId, address);
    }

    // GET
    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddresses(userId));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address deleted successfully");
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @RequestBody Address address) {

        return ResponseEntity.ok(addressService.updateAddress(id, address));
    }
}
