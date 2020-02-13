package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.config.AuthorizationService;
import com.cczora.armybuilder.config.AuthorizationServiceImpl;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.dto.DetachmentPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.DetachmentType;
import com.cczora.armybuilder.service.DetachmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
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

    @Operation(description = "Gets all detachments for the given army. If getFullDetachments flag is set to true, each detachment will be listed with its associated units")
    @GetMapping("/{armyId}")
    public ResponseEntity<List<?>> getDetachmentsForArmy(@PathVariable UUID armyId, @RequestParam(defaultValue = "false") boolean getFullDetachments, Principal principal) throws ValidationException{
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        if(getFullDetachments) {
            return ResponseEntity.ok(service.getFullDetachmentsByArmyId(armyId));
        }
        else {
            return ResponseEntity.ok(service.getDetachmentsByArmyId(armyId));
        }
    }

    @Operation(description = "Gets a single detachment with its associated units")
    @GetMapping("/{detachmentId}")
    public ResponseEntity<Detachment> getDetachment(@PathVariable UUID detachmentId, Principal principal) throws PersistenceException, ValidationException {
        if(principal != null) {
            authorizationService.validatePrincipalDetachment(principal, detachmentId);
        }
        return ResponseEntity.ok(service.getFullDetachment(detachmentId));
    }

    @Operation(description = "Gets a list of all possible detachment types")
    @GetMapping("/detachmentTypes")
    public ResponseEntity<List<DetachmentType>> getDetachmentTypes() {
        return ResponseEntity.ok(service.getAllDetachmentTypes());
    }

    @Operation(description = "Adds a detachment to the given army")
    @PostMapping
    public ResponseEntity<?> addDetachmentToArmy(
            @RequestParam UUID armyId, @RequestBody DetachmentDTO detachment, Principal principal) throws PersistenceException, ValidationException {
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        service.addDetachment(detachment);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(description = "Edits a detachment's properties")
    @PatchMapping("/{detachmentId}")
    public ResponseEntity<?> editDetachment(
            @RequestParam UUID armyId, @RequestBody DetachmentPatchRequestDTO dto, Principal principal) throws NoSuchFieldException, ValidationException {
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        service.editDetachment(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Deletes a detachment and its associated units")
    @DeleteMapping("/deleteDetachment/{armyId}/{detachmentId}")
    public ResponseEntity<?> deleteDetachment(@PathVariable UUID armyId, @PathVariable UUID detachmentId, Principal principal) throws NotFoundException, ValidationException {
        if(principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        service.deleteDetachment(detachmentId, armyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
