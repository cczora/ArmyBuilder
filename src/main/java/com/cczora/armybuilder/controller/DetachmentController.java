package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.config.AuthorizationService;
import com.cczora.armybuilder.config.AuthorizationServiceImpl;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.dto.DetachmentPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.DetachmentType;
import com.cczora.armybuilder.service.DetachmentService;
import com.cczora.armybuilder.service.MyUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(AppConstants.basePath + "detachment")
public class DetachmentController {

    private final DetachmentService service;
    private final AuthorizationService authorizationService;

    @Autowired
    public DetachmentController(DetachmentService service, AuthorizationServiceImpl authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }

    @Operation(summary = "Gets all detachments for the given army.",
            description = "If getFullDetachments = true, each detachment will be listed with its associated units")
    @GetMapping("/{armyId}")
    public ResponseEntity<List<?>> getDetachmentsForArmy(
            @PathVariable UUID armyId,
            @RequestParam(defaultValue = "false") boolean getFullDetachments,
            MyUserPrincipal principal) throws ValidationException, PersistenceException {
        if (principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        if (getFullDetachments) {
            return ResponseEntity.ok(service.getFullDetachmentsByArmyId(armyId));
        } else {
            return ResponseEntity.ok(service.getDetachmentsByArmyId(armyId));
        }
    }

    @Operation(summary = "Gets a single detachment with its associated units")
    @GetMapping("/{id}")
    public ResponseEntity<Detachment> getDetachment(@PathVariable(name = "id") UUID detachmentId, MyUserPrincipal principal)
            throws PersistenceException, ValidationException {
        if (principal != null) {
            authorizationService.validatePrincipalDetachment(principal, detachmentId);
        }
        return ResponseEntity.ok(service.getFullDetachment(detachmentId));
    }

    @Operation(summary = "Gets the list of detachment types")
    @GetMapping("/detachmentTypes")
    public ResponseEntity<List<DetachmentType>> getDetachmentTypes() {
        return ResponseEntity.ok(service.getAllDetachmentTypes());
    }

    @Operation(summary = "Adds a detachment to the given army")
    @PostMapping
    public void addDetachmentToArmy(
            @RequestParam UUID armyId, @RequestBody DetachmentDTO detachment, MyUserPrincipal principal)
            throws PersistenceException, ValidationException {
        if (principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        service.addDetachment(detachment);
    }


    @Operation(summary = "Edits a detachment's properties")
    @PatchMapping
    public void editDetachment(@RequestBody DetachmentPatchRequestDTO dto, MyUserPrincipal principal)
            throws NoSuchFieldException, ValidationException, PersistenceException {
        if (principal != null) {
            authorizationService.validatePrincipalDetachment(principal, dto.getDetachmentId());
        }
        service.editDetachment(dto);
    }

    @Operation(summary = "Deletes a detachment", description = "If deleteDetachment flag is set to false, will delete units but not the detachment itself.")
    @DeleteMapping("/{id}")
    public void deleteDetachment(
            @RequestParam(defaultValue = "true") Boolean deleteDetachment,
            @PathVariable(name = "id") UUID detachmentId,
            MyUserPrincipal principal) throws NotFoundException, ValidationException, PersistenceException {
        if (principal != null) {
            authorizationService.validatePrincipalDetachment(principal, detachmentId);
        }
        if (deleteDetachment) {
            service.deleteDetachment(detachmentId);
        } else {
            service.deleteUnitsForDetachment(detachmentId);
        }
    }

}
