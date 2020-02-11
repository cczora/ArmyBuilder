package com.cczora.armybuilder.config;

import com.cczora.armybuilder.data.ArmyRepository;
import com.cczora.armybuilder.data.UserRepository;
import com.cczora.armybuilder.models.entity.Account;
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

    //TODO: BIG CHANGES HERE once JWT is introduced

    @Override
    public void validatePrincipalUser(Principal principle, String username) throws ValidationException {
        Optional<Account> user = userRepo.findById(username);
        if(user.isPresent() && !principle.getName().equals(username)) {
            log.error("User {} is not allowed to access armies for {}", principle.getName(), username);
            throw new ValidationException(principle.getName());
        }
    }

    @Override
    public void validatePrincipalArmy(Principal principal, UUID armyId) throws ValidationException {
        Optional<Account> user = Optional.of(userRepo.findAccountByArmyId(armyId));
        String armyUsername = armyRepo.findById(armyId).get().getUsername();
        if(user.isPresent() && !principal.getName().equals(armyUsername)) {
            log.error("User {} is not allowed to access armies for {}", principal.getName(), armyUsername);
            throw new ValidationException(principal.getName());
        }
    }
}
