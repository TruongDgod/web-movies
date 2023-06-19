package com.bepay.application.repository.db;

import com.bepay.application.entities.db.CMSRoleEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends AbstractRepository<CMSRoleEntity, Long> {
    @Query(value = "SELECT role FROM CMSRoleEntity role WHERE role.name = :name")
    CMSRoleEntity findByName(@Param("name") String name);



}
