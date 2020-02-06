package com.cczora.armybuilder.config;

import java.security.Principal;

public interface AuthorizationService {

    void validatePrincipalUser(Principal principle, String username);

}
