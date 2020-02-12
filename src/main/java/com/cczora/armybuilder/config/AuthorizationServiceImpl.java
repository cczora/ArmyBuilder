package com.cczora.armybuilder.config;

import com.cczora.armybuilder.data.ArmyRepository;
import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.UserRepository;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.models.entity.Army;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private static final String NOT_ALLOWED = "User {} is not allowed to access armies for {}";

    //TODO: BIG CHANGES HERE once JWT is introduced

    @Override
    public void validatePrincipalUser(Principal principal, String username) throws ValidationException {
        Optional<Account> user = userRepo.findById(username);
        if(user.isPresent() && !principal.getName().equals(username)) {
            log.error(NOT_ALLOWED, principal.getName(), username);
            throw new ValidationException(principal.getName());
        }
    }

    @Override
    public void validatePrincipalArmy(Principal principal, UUID armyId) throws ValidationException {
        Optional<Account> user = userRepo.findAccountByArmyId(armyId);
        Optional<Army> army = armyRepo.findById(armyId);
        if(user.isPresent()  && army.isPresent() && !principal.getName().equals(army.get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getName(), army.get().getUsername());
            throw new ValidationException(principal.getName());
        }
    }

    @Override
    public void validatePrincipalDetachment(Principal principal, UUID detachmentId) throws ValidationException {
        Optional<Account> user = userRepo.findAccountByDetachmentId(detachmentId);
        Optional<Army> army = armyRepo.findById(detachmentRepo.findArmyIdForDetachment(detachmentId));
        if(user.isPresent() && army.isPresent() && !principal.getName().equals(army.get().getUsername())) {
            log.error(NOT_ALLOWED, principal.getName(), army.get().getUsername());
        }
    }
}
