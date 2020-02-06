package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.Account;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Account, String> {

    @Query(value = "select a.username, a.password, a.profilePicUrl from Account a where a.username = :username")
    Account findByUsername(@Param("username") String username) throws UsernameNotFoundException;

}
