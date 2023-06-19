package com.bepay.application.repository.db;

import com.bepay.application.dto.DataActionDTO;
import com.bepay.application.dto.DataGroupDTO;
import com.bepay.application.dto.ServiceGroupDTO;
import com.bepay.application.entities.db.CMSRoleEntity;
import com.bepay.application.entities.db.DataGroupEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DataGroupRepository extends AbstractRepository<DataGroupEntity, BigDecimal> {
    @Query( value = "SELECT DISTINCT" +
            " new com.bepay.application.dto.DataGroupDTO(dt.id, ac.configValue, role.id, dt.code, dt.status)  " +
            " FROM DataGroupEntity dt " +
            " INNER JOIN AppConfigEntity ac ON dt.id = ac.objId " +
            " INNER JOIN CMSRoleEntity role ON role.id = dt.roleId " +
            " WHERE 1 = 1 " +
            " AND (:name IS NULL OR (:name IS NOT NULL AND UPPER(ac.configValue) LIKE CONCAT('%',UPPER(:name) ,'%') ))" +
            " AND dt.roleId in (:roles)  " +
            " AND dt.status in (:status)  " +
            " AND ac.objType= 'GROUP_DATA' " +
            " AND ac.language = 'en' " +
            " AND dt.isDeleted = 0 " +
            " ORDER BY dt.id DESC")
    Page<DataGroupDTO> search(@Param("name") String name,
                              @Param("roles") List<BigDecimal> roles,
                              @Param("status") List<Integer> status,
                              Pageable pageable );
    @Query( value = "SELECT distinct" +
            " new com.bepay.application.dto.DataGroupDTO(dt.id,ac.configValue, role.id, dt.code, dt.status)  " +
            " FROM DataGroupEntity dt " +
            " INNER JOIN AppConfigEntity ac ON dt.id = ac.objId " +
            " INNER JOIN CMSRoleEntity role ON role.id = dt.roleId " +
            " WHERE 1 = 1 " +
            " AND (:name IS NULL OR (:name IS NOT NULL AND ac.configValue = :name ))" +
            " AND (:serviceCode IS NULL OR (:serviceCode IS NOT NULL AND dt.serviceCode = :serviceCode )) " +
            " AND (:status IS NULL OR (:status IS NOT NULL AND dt.status = :status )) " +
            " AND ac.configValue = 'GROUP_DATA_NAME' " +
            " AND dt.isDeleted = 0 ")
    List<DataGroupDTO> getDetail(@Param("name") String name,
                                 @Param("serviceCode") String serviceCode,
                                 @Param("status") Integer status,
                                 Pageable pageable );
    @Query( value = "SELECT distinct role" +
            " FROM DataGroupEntity dt " +
            " INNER JOIN CMSRoleEntity role ON role.id = dt.roleId " +
            " WHERE 1 = 1 " +
            " AND dt.id = :groupId" +
            " AND dt.isDeleted = 0 ")
    List<CMSRoleEntity> getRole(@Param("groupId") BigDecimal groupId);
    @Query( value = "SELECT distinct" +
            " new com.bepay.application.dto.ServiceGroupDTO(dt.id, ac.configValue)  " +
            " FROM DataGroupEntity dt " +
            " INNER JOIN AppConfigEntity ac ON  dt.name = ac.configKey " +
            " WHERE 1 = 1 " +
            " AND (:roleId IS NULL OR (:roleId IS NOT NULL AND (dt.roleId = :roleId)))" +
            " AND ac.objType = 'GROUP_DATA' " +
            " AND dt.isDeleted = 0 " +
            " AND ac.language = 'en'" +
            " ORDER BY dt.id DESC")
    List<ServiceGroupDTO> getGroup(@Param("roleId") BigDecimal roleId);

    @Query( value = "SELECT distinct dt.status" +
            " FROM DataGroupEntity dt " +
            " WHERE 1 = 1 " +
            " AND dt.isDeleted = 0 ")
    List<Integer> getStatus();


    @Query( value = "SELECT distinct" +
            " new com.bepay.application.dto.ServiceGroupDTO(dt.id, ac.configValue)  " +
            " FROM DataGroupEntity dt " +
            " INNER JOIN AppConfigEntity ac ON  dt.name = ac.configKey " +
            " WHERE 1 = 1 " +
            " AND dt.id = :id" +
            " AND ac.objType = 'GROUP_DATA' " +
            " AND dt.isDeleted = 0 " +
            " AND ac.language = 'en'" +
            " ORDER BY dt.id DESC")
    ServiceGroupDTO getServiceGroupDetail(@Param("id") BigDecimal id);


    @Query( value = "SELECT distinct dt.roleId" +
            " FROM DataGroupEntity dt " +
            " WHERE 1 = 1 " +
            " AND dt.isDeleted = 0 ")
    List<Integer> getRole();

    @Query( value = "SELECT DISTINCT" +
            " new com.bepay.application.dto.DataActionDTO( dt.id,ac.configValue, dt.code)  " +
            " FROM DataGroupEntity dt " +
            " INNER JOIN AppConfigEntity ac ON dt.id = ac.objId " +
            " WHERE 1 = 1 " +
            " AND (:code IS NULL OR (:code IS NOT NULL AND dt.code = :code ))" +
            " AND dt.status = 1  " +
            " AND ac.objType= 'GROUP_DATA' " +
            " AND ac.language = 'en' " +
            " AND dt.isDeleted = 0 ")
    DataActionDTO searchPackage(@Param("code") String code);


    @Query( value = "SELECT  dt" +
            " FROM DataGroupEntity dt " +
            " WHERE 1 = 1 " +
            " AND dt.code like :code" +
            " AND dt.isDeleted = 0 ")
    List<DataGroupEntity> getCode(@Param("code") String code);

//    @Query( value = "SELECT  dt" +
//            " FROM DataGroupEntity dt " +
//            " INNER JOIN AppConfigEntity ac ON dt.name = ac.configKey " +
//            " WHERE 1 = 1 " +
//            " AND ac.configValue = :name" +
//            " AND dt.roleId = :role" +
//            " AND dt.isDeleted = 0 ")
//    List<DataGroupEntity> checkName(@Param("code") String name,
//                                    @Param("code") Integer role);


    @Query( value = "SELECT DISTINCT" +
            " new com.bepay.application.dto.DataActionDTO(dt.id, ac.configValue, dt.code)  " +
            " FROM DataGroupEntity dt " +
            " INNER JOIN AppConfigEntity ac ON dt.id = ac.objId " +
            " WHERE 1 = 1 " +
            " AND dt.status = 1  " +
            " AND ac.objType= 'GROUP_DATA' " +
            " AND ac.language = 'en' " +
            " AND dt.isDeleted = 0 ")
    List<DataActionDTO> getListAction();

    @Query( value = "SELECT  dt" +
        " FROM DataGroupEntity dt " +
        " INNER JOIN AppConfigEntity ac ON dt.name = ac.configKey " +
        " WHERE 1 = 1 " +
        " AND (:name is null or (:name is not null and ac.configValue = :name))" +
        " AND dt.isDeleted = 0 ")
    List<DataGroupEntity> getName(@Param("name") String name);



}
