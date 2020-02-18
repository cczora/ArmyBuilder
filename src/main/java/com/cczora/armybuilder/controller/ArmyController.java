package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.config.AuthorizationService;
import com.cczora.armybuilder.config.AuthorizationServiceImpl;
import com.cczora.armybuilder.config.exception.DuplicateUsernameException;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
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
@RequestMapping(AppConstants.basePath + "/army")
public class ArmyController {

    private final ArmyService service;
    private final AuthorizationService authorizationService;

    @Autowired
    public ArmyController(ArmyService service, AuthorizationServiceImpl authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }

    @Operation(description = "Get all armies for a given username")
    @GetMapping("/{username}")
    public ResponseEntity<List<ArmyDTO>> getArmiesByUsername(@PathVariable String username, Principal principal) throws ValidationException, PersistenceException, NotFoundException {
        if(principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.getArmiesByUsername(username));
    }

    @Operation(description = "Adds an army to the given user's list of armies")
    @PostMapping("/{username}")
    public ResponseEntity<?> addArmy(
            @PathVariable String username,
            @RequestBody @Valid ArmyDTO army,
            Principal principal) throws PersistenceException , ValidationException, NotFoundException {
        if(principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        service.addArmy(army, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Edits an army by username ")
    @PatchMapping("/{username}")
    public ResponseEntity<?> editArmy(@PathVariable String username,
                                            @RequestBody ArmyPatchRequestDTO army,
                                            Principal principal) throws PersistenceException, ValidationException, NotFoundException, NoSuchFieldException {
        if(principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        service.editArmy(army);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Deletes an army ")
    @DeleteMapping("/{username}/{armyId}")
    public ResponseEntity<?> deleteArmy(@PathVariable String username, @PathVariable UUID armyId, Principal principal) throws ValidationException, PersistenceException, NotFoundException {
        if(principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        service.deleteArmyById(armyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
