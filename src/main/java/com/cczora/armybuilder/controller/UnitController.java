package com.cczora.armybuilder.controller;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.entity.UnitType;
import com.cczora.armybuilder.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(AppConstants.basePath)
public class UnitController {

    private final UnitService service;

    @Autowired
    public UnitController(UnitService service) {
        this.service = service;
    }

    @GetMapping("/units/{armyId}/{detachmentId}")
    public ResponseEntity<List<Unit>> getUnitsForDetachment(@PathVariable UUID armyId, @PathVariable UUID detachmentId) {
        List<Unit> units = service.getUnitsForDetachment(detachmentId);
        return ResponseEntity.ok(units);
    }

    @GetMapping("/unitTypes")
    public ResponseEntity<List<UnitType>> getUnitTypes() {
        List<UnitType> unitTypes = service.getAllUnitTypes();
        return ResponseEntity.ok(unitTypes);
    }

    @PostMapping("/addUnit/{armyId}")
    public ResponseEntity<Unit> addUnitToDetachment(@RequestBody Unit unit, @PathVariable UUID armyId) {
        Unit newUnit = service.addUnit(unit, unit.getDetachmentId(), armyId);
        return ResponseEntity.ok(newUnit);
    }

    @PutMapping("/editUnit/{unitId}")
    public ResponseEntity<Unit> editUnit(@PathVariable UUID unitId, @RequestBody Unit unit) {
        Unit editedUnit = service.editUnit(unitId, unit);
        if (editedUnit != null) {
            return ResponseEntity.ok(editedUnit);
        }
        return ResponseEntity.badRequest().body(unit);
    }

    @DeleteMapping("/deleteUnit/{unitId}")
    public ResponseEntity deleteUnit(@PathVariable UUID unitId) {
        if (service.deleteUnitById(unitId)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
