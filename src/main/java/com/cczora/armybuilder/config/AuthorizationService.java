package com.cczora.armybuilder.config;

import com.cczora.armybuilder.service.MyUserPrincipal;

import javax.validation.ValidationException;
import java.util.UUID;

public interface AuthorizationService {

    void validatePrincipalUser(MyUserPrincipal principle, String username) throws ValidationException;

    void validatePrincipalArmy(MyUserPrincipal principal, UUID armyId) throws ValidationException;

    void validatePrincipalDetachment(MyUserPrincipal principal, UUID detachmentId) throws ValidationException;

    void validatePrincipalUnit(MyUserPrincipal principal, UUID unitId) throws ValidationException;
}
