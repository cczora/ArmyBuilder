package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.config.AuthorizationService;
import com.cczora.armybuilder.models.dto.UnitDTO;
import com.cczora.armybuilder.models.dto.UnitPatchRequestDTO;
import com.cczora.armybuilder.models.entity.UnitType;
import com.cczora.armybuilder.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.ValidationException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(AppConstants.basePath + "/unit")
public class UnitController {

    private final UnitService service;
    private final AuthorizationService authorizationService;

    @Autowired
    public UnitController(UnitService service, AuthorizationService authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }

    @Operation(description = "Get all units for the given detachment")
    @GetMapping("/{detachmentId}")
    public ResponseEntity<List<UnitDTO>> getUnitsForDetachment(@PathVariable UUID detachmentId, Principal principal) {
        if(principal != null) {
            authorizationService.validatePrincipalDetachment(principal, detachmentId);
        }
        return ResponseEntity.ok(service.getUnitsForDetachment(detachmentId));
    }

    @GetMapping("/unitTypes")
    public ResponseEntity<List<UnitType>> getUnitTypes() {
        return ResponseEntity.ok(service.getAllUnitTypes());
    }

    @PostMapping
    public ResponseEntity<UnitDTO> addUnitToDetachment(@RequestBody UnitDTO unit, Principal principal) throws ValidationException, NotFoundException {
        if (principal != null) {
            authorizationService.validatePrincipalUnit(principal, unit.getId());
        }
        UnitDTO newUnit = service.addUnit(unit);
        return ResponseEntity.ok(newUnit);
    }

    @PatchMapping("/{unitId}")
    public ResponseEntity<?> editUnit(@RequestBody UnitPatchRequestDTO dto, Principal principal) throws NoSuchFieldException, ValidationException {
        if (principal != null) {
            authorizationService.validatePrincipalUnit(principal, dto.getUnitId());
        }
        service.editUnit(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable UUID unitId, @RequestBody UUID detachmentId, Principal principal) throws NotFoundException, ValidationException {
        if(principal != null) {
            authorizationService.validatePrincipalUnit(principal, unitId);
        }
        service.deleteUnitById(unitId, detachmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
