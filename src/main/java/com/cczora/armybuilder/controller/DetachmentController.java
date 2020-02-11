package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.config.AuthorizationService;
import com.cczora.armybuilder.config.AuthorizationServiceImpl;
import com.cczora.armybuilder.models.dto.DetachmentPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.DetachmentType;
import com.cczora.armybuilder.service.DetachmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(AppConstants.basePath + "/detachment")
public class DetachmentController {

    private final DetachmentService service;
    private final AuthorizationService authorizationService;

    @Autowired
    public DetachmentController(DetachmentService service, AuthorizationServiceImpl authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }

    @Operation(description = "Gets all detachments for the armyId passed in")
    @GetMapping("/{armyId}")
    public ResponseEntity<List<Detachment>> getDetachmentsForArmy(@PathVariable UUID armyId, Principal principal) throws Exception {
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        List<Detachment> detachments = service.getDetachmentsByArmyId(armyId);
        return ResponseEntity.ok(detachments);
    }

    @Operation(description = "Gets a list of all possible detachment types")
    @GetMapping("/detachmentTypes")
    public ResponseEntity<List<DetachmentType>> getDetachmentTypes() {
        return ResponseEntity.ok(service.getAllDetachmentTypes());
    }

    @Operation(description = "Adds a detachment to the given army")
    @PostMapping("/{armyId}")
    public ResponseEntity<?> addDetachmenttoArmy(
            @PathVariable UUID armyId, @RequestBody Detachment detachment, Principal principal) throws Exception {
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        service.addDetachment(detachment, armyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/{armyId}/{detachmentId}")
    public ResponseEntity<?> editDetachment(
            @PathVariable UUID armyId, @RequestBody DetachmentPatchRequestDTO dto, Principal principal) throws Exception {
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        service.editDetachment(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteDetachment/{armyId}/{detachmentId}")
    public ResponseEntity<?> deleteDetachment(@PathVariable UUID armyId, @PathVariable UUID detachmentId, Principal principal) throws Exception {
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        service.deleteDetachment(detachmentId, armyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
