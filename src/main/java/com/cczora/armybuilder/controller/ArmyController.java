package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AuthorizationService;
import com.cczora.armybuilder.config.AuthorizationServiceImpl;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.service.ArmyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ArmyController {

    private final ArmyService service;
    private final AuthorizationService authorizationService;

    @Autowired
    public ArmyController(ArmyService service, AuthorizationServiceImpl authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }

    @Operation(description = "Get all armies for a given username")
    @GetMapping("/armies/{username}")
    public ResponseEntity<List<ArmyDTO>> getArmiesByUsername(@PathVariable String username, Principal principal) {
        //TODO: use auth service here
        if (principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.OK).body(service.getArmiesByUsername(username));
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @Operation(description = "Adds an army to the given user's list of armies")
    @PostMapping("/addArmy/{username}")
    public ResponseEntity<List<Army>> addArmy(
            @PathVariable String username,
            @RequestBody @Valid ArmyDTO army,
            Principal principal) throws Exception {
        if(principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        List<Army> armies = service.addArmy(army, username);
        if (armies != null && armies.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(armies);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
    }

    @Operation(description = "Edits an army by username and armyId")
    @PutMapping("/editArmy/{username}/{armyId}")
    public ResponseEntity<Army> editArmy(@PathVariable String username, @PathVariable UUID armyId, @RequestBody @Valid Army army) {
        //TODO: use auth service here
        Army editedArmy = service.editArmy(username, armyId, army);
        return ResponseEntity.ok(editedArmy);
    }

    @Operation(description = "Deletes an army ")
    @DeleteMapping("/deleteArmy/{username}/{armyId}")
    public ResponseEntity deleteArmy(@PathVariable String username, @PathVariable UUID armyId, Principal principal) throws Exception {
        if(principal != null) {
            authorizationService.validatePrincipalUser(principal, username);
        }
        service.deleteArmyById(armyId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
