package com.bepay.application.repository.db;

import com.bepay.application.dto.DataPackageDTO;
import com.bepay.application.dto.request.DataPackageActionDTO;
import com.bepay.application.entities.db.DataGroupEntity;
import com.bepay.application.entities.db.DataPackageEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface DataPackageRespository extends AbstractRepository<DataPackageEntity, BigDecimal> {
    @Query(value = "SELECT distinct" +
            " new com.bepay.application.dto.DataPackageDTO(pk.id,ac.configValue,pk.roleId,pk.code,dt.name,pk.status) " +
            " FROM DataPackageEntity pk " +
            " INNER JOIN DataGroupEntity dt ON pk.groupDataId = dt.id " +
            " INNER JOIN AppConfigEntity ac ON ac.configKey = pk.shortName " +
            " WHERE 1 = 1 " +
            " AND (:name IS NULL OR ( :name IS NOT NULL AND UPPER(ac.configValue)  LIKE CONCAT('%',UPPER(:name) ,'%')  )) " +
            " AND (:code IS NULL OR ( :code IS NOT NULL AND UPPER(pk.code)  LIKE CONCAT('%',UPPER(:code) ,'%')  )) " +
            " AND pk.status in (:status) " +
            " AND pk.groupDataId in (:groupDataIds)  " +
            " AND ac.language = 'en'" +
            " AND pk.roleId in (:role) " +
            " AND pk.isDeleted = 0 " +
            " ORDER BY pk.id DESC")
    Page<DataPackageDTO> search(@Param("name") String name,
                                @Param("status") List<Integer> status,
                                @Param("groupDataIds")Set<BigDecimal> groupDataIds,
                                @Param("role") List<BigDecimal> role,
                                @Param("code") String code,
                                Pageable pageable) ;

    @Query(value = "SELECT distinct" +
            " new com.bepay.application.dto.request.DataPackageActionDTO(pk.id,ac.configValue,pk.code) " +
            " FROM DataPackageEntity pk " +
            " INNER JOIN AppConfigEntity ac ON ac.configKey = pk.name " +
            " WHERE 1 = 1 " +
            " AND pk.status = 1 " +
            " AND pk.groupDataId = :groupDataId " +
            " AND pk.isDeleted = 0 " )
    List<DataPackageActionDTO> searchDataGroup(@Param("groupDataId") BigDecimal groupDataId) ;

    @Query(value = "SELECT distinct" +
            " new com.bepay.application.dto.request.DataPackageActionDTO(pk.id,ac.configValue,pk.code) " +
            " FROM DataPackageEntity pk " +
            " INNER JOIN AppConfigEntity ac ON ac.configKey = pk.name " +
            " WHERE 1 = 1 " +
            " AND pk.status = 1 " +
            " AND pk.code = :code " +
            " AND pk.isDeleted = 0 " +
            " AND ac.language = 'en'" )
    DataPackageActionDTO getDataPackageAction(@Param("code") String code) ;

    @Query( value = "SELECT  dp" +
            " FROM DataPackageEntity dp " +
            " WHERE 1 = 1 " +
            " AND dp.code like :code" +
            " AND dp.isDeleted = 0 ")
    DataPackageEntity getCode(@Param("code") String code);

    @Query( value = "SELECT  pk" +
            " FROM DataPackageEntity pk " +
            " WHERE 1 = 1 " +
            " AND pk.groupDataId = :groupID" +
            " AND pk.isDeleted = 0 ")
    List<DataPackageEntity> getAllByGroupDataId(@Param("groupID") BigDecimal groupID);


}
