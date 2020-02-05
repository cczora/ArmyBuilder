package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public abstract class UserRepository implements JpaRepository<Account, String> {

    @Query(value = "select * from account a where a.username = ?", nativeQuery = true)
    public abstract Account findByUsername(String username);

}
