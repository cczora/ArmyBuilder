package com.cczora.armybuilder.config;

import com.cczora.armybuilder.data.ArmyRepository;
import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.UserRepository;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.service.MyUserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.validation.ValidationException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
//this service validates the incoming valid JWT to see if it is the same user as the army/detachment/unit that we are trying to access
public class AuthorizationServiceImpl implements AuthorizationService {

    private UserRepository userRepo;
    private ArmyRepository armyRepo;
    private DetachmentRepository detachmentRepo;
    private UnitRepository unitRepo;

    private static final String NOT_ALLOWED = "User {} is not allowed to access armies for {}";
    private static final String USER_NOT_FOUND = "User {} not found.";

    @Override
    public void validatePrincipalUser(MyUserPrincipal principal, String username) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findById(username);
        if (user.isPresent() && !principal.getUsername().equals(username)) {
            log.error(NOT_ALLOWED, principal.getUsername(), username);
            throw new ValidationException(principal.getUsername());
        }
        if (user.isEmpty()) {
            log.error(USER_NOT_FOUND, username);
            throw new NotFoundException(username);
        }
    }

    @Override
    public void validatePrincipalArmy(MyUserPrincipal principal, UUID armyId) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findAccountByArmyId(armyId);
        Optional<Army> army = armyRepo.findById(armyId);
        if (user.isPresent() && army.isPresent() && !principal.getUsername().equals(army.get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getUsername(), army.get().getUsername());
            throw new ValidationException(principal.getUsername());
        }
        if (user.isEmpty()) {
            log.error("Army {} is not associated with a user.", armyId);
            throw new NotFoundException("User for army " + armyId.toString());
        }
        if (army.isEmpty()) {
            log.error("Army {} not found", armyId);
            throw new NotFoundException("Army " + armyId.toString());
        }
    }

    @Override
    public void validatePrincipalDetachment(MyUserPrincipal principal, UUID detachmentId) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findAccountByDetachmentId(detachmentId);
        Optional<Army> army = armyRepo.findById(detachmentRepo.findArmyIdForDetachment(detachmentId));
        if (user.isPresent() && army.isPresent() && !principal.getUsername().equals(army.get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getUsername(), army.get().getUsername());
        }
        if (user.isEmpty()) {
            log.error("Detachment {} is not associated with a user.", detachmentId);
            throw new NotFoundException("User for detachment " + detachmentId.toString());
        }
        if (army.isEmpty()) {
            log.error("Detachment {} is not associated with an army.", detachmentId);
            throw new NotFoundException("Army for detachment " + detachmentId.toString());
        }
    }

    @Override
    public void validatePrincipalUnit(MyUserPrincipal principal, UUID unitId) throws ValidationException, NotFoundException {
        Optional<Account> user = userRepo.findAccountByUnitId(unitId);
        Optional<Detachment> detachmentForUnit = detachmentRepo.findById(unitRepo.findDetachmentIdForUnit(unitId));
        Optional<UUID> armyId = Optional.ofNullable(unitRepo.findArmyIdForUnit(unitId));
        if (armyId.isPresent() && user.isPresent() && detachmentForUnit.isPresent() && !principal.getUsername().equals(armyRepo.findById(armyId.get()).get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getUsername(), armyRepo.findById(armyId.get()).get().getUsername());
        }
        if (user.isEmpty()) {
            log.error("Unit {} is not associated with a user.", unitId);
            throw new NotFoundException("User for unit " + unitId.toString());
        }
        if (detachmentForUnit.isEmpty()) {
            log.error("Unit {} is not associated with a detachment.", unitId);
            throw new NotFoundException("Detachment for unit " + unitId.toString());
        }
    }
}
