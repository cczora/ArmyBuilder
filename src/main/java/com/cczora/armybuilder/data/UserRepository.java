package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.Account;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Account, String> {

    @Query(value = "select a.username, a.password, a.profilePicUrl from Account a where a.username in (select distinct a.username from Army a where a.army_id = :armyId)")
    Account findAccountByArmyId(@Param("armyId") UUID armyId);

}
