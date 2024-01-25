package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.PropertyTeamMemberDTO;
import com.hostfully.bookingapi.services.PropertyTeamMemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@Tag(name = "Property Team Member API", description = "Creates a new property team member in the system. A team member can be either a MANAGER or OWNER")
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
    public ResponseEntity<ApiResponseDTO<UUID>> createPropertyTeamMembers(@RequestBody @Valid PropertyTeamMemberDTO propertyDTO){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyTeamMemberService.createPropertyTeamMember(propertyDTO)));
    }

    @PutMapping()
    public ResponseEntity updatePropertyTeamMembers(@RequestBody @Valid PropertyTeamMemberDTO propertyDTO){
        propertyTeamMemberService.updatePropertyTeamMember(propertyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{teamMemberUUID}")
    public ResponseEntity deletePropertyTeamMembers(@PathVariable UUID teamMemberUUID){
        propertyTeamMemberService.deletePropertyTeamMember(teamMemberUUID);
        return ResponseEntity.ok().build();
    }
}
