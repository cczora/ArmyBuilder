package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.DetachmentType;
import com.cczora.armybuilder.service.DetachmentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Slf4j
public class DetachmentController {

    private final DetachmentService service;
    private final Logger log = LoggerFactory.getLogger(DetachmentController.class);

    @Autowired
    public DetachmentController(DetachmentService service) {
        this.service = service;
    }

    @GetMapping("/detachments/{armyId}")
    public ResponseEntity<List<Detachment>> getDetachmentsForArmy(@PathVariable UUID armyId) {
        List<Detachment> detachments = service.getDetachmentsByArmyId(armyId);
        return ResponseEntity.ok(detachments);
    }

    @GetMapping("/detachmentTypes")
    public ResponseEntity<List<DetachmentType>> getDetachmentTypes() {
        List<DetachmentType> detachmentTypes = service.getAllDetachmentTypes();
        return ResponseEntity.ok(detachmentTypes);
    }

    @PostMapping("/addDetachment/{armyId}")
    public ResponseEntity<Detachment> addDetachmenttoArmy(@PathVariable UUID armyId, @RequestBody Detachment detachment) {
//        if (principal.getName().equals(username)) {
        Detachment newDetachment = new Detachment();
        try {
            newDetachment = service.addDetachment(detachment, armyId);
        }
        catch(Exception e) {
            log.error("Error adding detachments for army {}", armyId);
            System.out.println(e.getMessage());
        }
            return ResponseEntity.ok(newDetachment);
//        }
//        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/editDetachment/{armyId}/{detachmentId}")
    public ResponseEntity<List<Detachment>> editDetachment(@PathVariable UUID armyId, @PathVariable UUID detachmentId, @RequestBody Detachment detachment) {
        List<Detachment> detachments = service.editDetachment(detachmentId, armyId, detachment);
        if (detachments != null) {
            return ResponseEntity.ok(detachments);
        }
        return ResponseEntity.badRequest().body(detachments);
    }

    @DeleteMapping("/deleteDetachment/{armyId}/{detachmentId}")
    public ResponseEntity<List<Detachment>> deleteDetachment(@PathVariable UUID armyId, @PathVariable UUID detachmentId) {
        List<Detachment> detachments = service.deleteDetachment(detachmentId, armyId);
        return ResponseEntity.ok(detachments);
    }

}
