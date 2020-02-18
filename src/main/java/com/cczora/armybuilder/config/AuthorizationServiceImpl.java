package com.cczora.armybuilder.config;

import com.cczora.armybuilder.data.ArmyRepository;
import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.UserRepository;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.Detachment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.validation.ValidationException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private UserRepository userRepo;
    private ArmyRepository armyRepo;
    private DetachmentRepository detachmentRepo;
    private UnitRepository unitRepo;

    private static final String NOT_ALLOWED = "User {} is not allowed to access armies for {}";
    private static final String USER_NOT_FOUND = "User {} not found.";

    //TODO: BIG CHANGES HERE once JWT is introduced

    @Override
    public void validatePrincipalUser(Principal principal, String username) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findById(username);
        if(user.isPresent() && !principal.getName().equals(username)) {
            log.error(NOT_ALLOWED, principal.getName(), username);
            throw new ValidationException(principal.getName());
        }
        if(user.isEmpty()) {
            log.error(USER_NOT_FOUND, username);
            throw new NotFoundException(username);
        }
    }

    @Override
    public void validatePrincipalArmy(Principal principal, UUID armyId) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findAccountByArmyId(armyId);
        Optional<Army> army = armyRepo.findById(armyId);
        if(user.isPresent()  && army.isPresent() && !principal.getName().equals(army.get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getName(), army.get().getUsername());
            throw new ValidationException(principal.getName());
        }
        if(user.isEmpty()) {
            log.error("Army {} is not associated with a user.", armyId);
            throw new NotFoundException("User for army " + armyId.toString());
        }
        if(army.isEmpty()) {
            log.error("Army {} not found", armyId);
            throw new NotFoundException("Army " + armyId.toString());
        }
    }

    @Override
    public void validatePrincipalDetachment(Principal principal, UUID detachmentId) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findAccountByDetachmentId(detachmentId);
        Optional<Army> army = armyRepo.findById(detachmentRepo.findArmyIdForDetachment(detachmentId));
        if(user.isPresent() && army.isPresent() && !principal.getName().equals(army.get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getName(), army.get().getUsername());
        }
        if(user.isEmpty()) {
            log.error("Detachment {} is not associated with a user.", detachmentId);
            throw new NotFoundException("User for detachment " + detachmentId.toString());
        }
        if(army.isEmpty()) {
            log.error("Detachment {} is not associated with an army.", detachmentId);
            throw new NotFoundException("Army for detachment " + detachmentId.toString());
        }
    }

    @Override
    public void validatePrincipalUnit(Principal principal, UUID unitId) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findAccountByUnitId(unitId);
        Optional<Detachment> detachmentForUnit = detachmentRepo.findById(unitRepo.findDetachmentIdForUnit(unitId));
        UUID armyId = detachmentRepo.findArmyIdForDetachment(unitRepo.findDetachmentIdForUnit(unitId));
        if(user.isPresent() && detachmentForUnit.isPresent() && !principal.getName().equals(armyRepo.findById(armyId).get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getName(), armyRepo.findById(armyId).get().getUsername());
        }
        if(user.isEmpty()) {
            log.error("Unit {} is not associated with a user.", unitId);
            throw new NotFoundException("User for unit " + unitId.toString());
        }
        if(detachmentForUnit.isEmpty()) {
            log.error("Unit {} is not associated with a detachment.", unitId);
            throw new NotFoundException("Detachment for unit " + unitId.toString());
        }
    }
}
