package com.cczora.armybuilder.config;

import javax.validation.ValidationException;
import java.security.Principal;
import java.util.UUID;

public interface AuthorizationService {

    void validatePrincipalUser(Principal principle, String username) throws ValidationException;

    void validatePrincipalArmy(Principal principal, UUID armyId) throws ValidationException;

}
