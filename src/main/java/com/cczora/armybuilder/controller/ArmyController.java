package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.config.AuthorizationService;
import com.cczora.armybuilder.config.AuthorizationServiceImpl;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.service.ArmyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(AppConstants.basePath + "army")
public class ArmyController {

    private final ArmyService service;
    private final AuthorizationService authorizationService;

    @Autowired
    public ArmyController(ArmyService service, AuthorizationServiceImpl authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }

    @Operation(summary = "Get a single army's details")
    @GetMapping("/{id}")
    public ResponseEntity<Army> getArmy(@PathVariable(name = "id") UUID armyId, Principal principal)
            throws ValidationException, NotFoundException {
        if (principal != null) {
            authorizationService.validatePrincipalArmy(principal, armyId);
        }
        return ResponseEntity.ok(service.getArmyById(armyId));
    }

    @Operation(summary = "Gets all armies for the given user")
    @GetMapping("/{username}/armies")
    public ResponseEntity<List<ArmyDTO>> getArmiesByUsername(@PathVariable String username, Principal principal)
            throws ValidationException, PersistenceException, NotFoundException {
        if (principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.getArmiesByUsername(username));
    }

    @Operation(summary = "Adds an army for the given user")
    @PostMapping
    public ResponseEntity<UUID> addArmy(
            @RequestParam String username,
            @RequestBody @Valid ArmyDTO army,
            Principal principal) throws PersistenceException, ValidationException, NotFoundException {
        if (principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        ArmyDTO addedArmy = service.addArmy(army, username);
        return ResponseEntity.ok(addedArmy.getArmyId());
    }

    @Operation(summary = "Updates an army for the given user")
    @PatchMapping
    public void editArmy(@RequestParam String username,
                         @RequestBody ArmyPatchRequestDTO army,
                         Principal principal)
            throws PersistenceException, ValidationException, NotFoundException, NoSuchFieldException {
        if (principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        service.editArmy(army);
    }

    @Operation(summary = "Deletes an army")
    @DeleteMapping("/{id}")
    public void deleteArmy(@RequestParam String username, @PathVariable(name = "id") UUID armyId, Principal principal)
            throws ValidationException, PersistenceException, NotFoundException {
        if (principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        service.deleteArmyById(armyId);
    }

}
