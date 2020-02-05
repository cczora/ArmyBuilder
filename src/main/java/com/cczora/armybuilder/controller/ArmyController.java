package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.models.Army;
import com.cczora.armybuilder.service.ArmyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ArmyController {

    //TODO: add validation for users having access to armies

    private final ArmyService service;

    @Autowired
    public ArmyController(ArmyService service) {
        this.service = service;
    }

    @GetMapping("/armies/{username}")
    public ResponseEntity<List<Army>> getArmiesByUsername(@PathVariable String username, Principal principal) {
        if (principal.getName().equals(username)) {
            List<Army> armies = service.getArmiesByUsername(username);
            if (armies != null && armies.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(armies);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/addArmy/{username}")
    public ResponseEntity<List<Army>> addArmy(
            @PathVariable String username,
            @RequestBody @Valid Army army,
            Principal principal) {
        if (principal.getName().equals(username)) {
            List<Army> armies = service.addArmy(army, username);
            if (armies != null && armies.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(armies);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/editArmy/{username}/{armyId}")
    public ResponseEntity<Army> editArmy(@PathVariable String username, @PathVariable UUID armyId, @RequestBody @Valid Army army) {
        Army editedArmy = service.editArmy(username, armyId, army);
        return ResponseEntity.ok(editedArmy);
    }

    @DeleteMapping("/deleteArmy/{username}/{armyId}")
    public ResponseEntity<List<Army>> deleteArmy(@PathVariable String username, @PathVariable UUID armyId) {
        List<Army> armies = service.deleteArmyById(username, armyId);
        return ResponseEntity.ok(armies);
    }

}
