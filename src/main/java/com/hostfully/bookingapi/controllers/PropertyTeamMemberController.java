package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.property.PropertyDTO;
import com.hostfully.bookingapi.models.dto.propertyteammember.PropertyTeamMemberDTO;
import com.hostfully.bookingapi.services.PropertyService;
import com.hostfully.bookingapi.services.PropertyTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/property-team-members")
public class PropertyTeamMemberController {
    @Autowired
    PropertyTeamMemberService propertyTeamMemberService;

    @GetMapping("/detail/{teamMemberUUID}")
    public ResponseEntity<ApiResponseDTO<PropertyTeamMemberDTO>> getTeamMember(@PathVariable UUID teamMemberUUID){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyTeamMemberService.getTeamMember(teamMemberUUID)));
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<ArrayList<PropertyTeamMemberDTO>>> getAllTeamMembers(){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyTeamMemberService.getAllTeamMembers()));
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> createPropertyTeamMembers(@RequestBody PropertyTeamMemberDTO propertyDTO){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyTeamMemberService.createPropertyTeamMember(propertyDTO)));
    }

    @PutMapping()
    public ResponseEntity updatePropertyTeamMembers(@RequestBody PropertyTeamMemberDTO propertyDTO){
        propertyTeamMemberService.updatePropertyTeamMember(propertyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{teamMemberUUID}")
    public ResponseEntity deletePropertyTeamMembers(@PathVariable UUID propertyUUID){
        propertyTeamMemberService.deletePropertyTeamMember(propertyUUID);
        return ResponseEntity.ok().build();
    }
}
