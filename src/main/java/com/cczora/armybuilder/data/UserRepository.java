package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Account, String> {

    @Query(value = "select a.username, a.password, a.profilePicUrl from Account a where a.username in (select distinct a.username from Army a where a.id = :armyId)")
    Optional<Account> findAccountByArmyId(@Param("armyId") UUID armyId);

    @Query(value = "select a.username, a.password, a.profile_pic_url from Account a " +
            "where a.username = (" +
            "select distinct a.username from Army a inner join Detachment d on d.army_id = a.army_id where d.detachment_id = :detachmentId)", nativeQuery = true)
    Optional<Account> findAccountByDetachmentId(@Param("detachmentId") UUID detachmentId);

    @Query(value = "select a.username, a.password, a.profile_pic_url from Account a where a.username = (" +
            "select distinct ar.username from Army ar " +
            "inner join Detachment d on ar.army_id = d.army_id " +
            "inner join Unit u on d.detachment_id = u.detachment_id " +
            "where u.unit_id = :unitId)", nativeQuery = true)
    Optional<Account> findAccountByUnitId(@Param("unitId") UUID unitId);

}
