package com.bepay.application.repository.db;

import com.bepay.application.entities.db.AccountEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends AbstractRepository<AccountEntity, Long> {
    @Query(value = "SELECT acc FROM AccountEntity acc WHERE acc.userName = :userName")
    AccountEntity findByUserName(@Param("userName") String userName);
}
