package com.cczora.armybuilder.config;

import com.cczora.armybuilder.data.UserRepository;
import com.cczora.armybuilder.models.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private UserRepository userRepo;

    @Autowired
    public AuthorizationServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void validatePrincipalUser(Principal principle, String username) throws ValidationException {
        Optional<Account> user = userRepo.findById(username);
        if(user.isPresent() && !principle.getName().equals(username)) {
            log.error("User {} is not allowed to access armies for {}", principle.getName(), username);
            throw new ValidationException(principle.getName());
        }
    }
}
