package com.bepay.application.repository.db;

import com.bepay.application.entities.db.AppConfigEntity;
import com.bepay.application.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @see AppConfigRepository
 */
@Repository
public interface AppConfigRepository extends AbstractRepository<AppConfigEntity, Long> {
    @Query(value = "select a  FROM AppConfigEntity a " +
            "WHERE   a.configKey = :configKey and a.language =:language ")
    AppConfigEntity getByConfigKeyAndLanguage(String configKey, String language);


    @Query( value =  " FROM AppConfigEntity appConfig" +
            " WHERE appConfig.configKey = :configkey " )
    List<AppConfigEntity> findByObj(String configkey);


    @Query( value =  " FROM AppConfigEntity appConfig" +
            " WHERE appConfig.configKey = :configkey " +
            " AND appConfig.language = :language")
    AppConfigEntity findByObj(String configkey, String language);


    @Query(value = "select a  FROM AppConfigEntity a " +
            " WHERE   a.configValue = :value " +
            " AND a.objValue = 'GROUP_DATA_NAME'" +
            " and a.language =:language ")
    List<AppConfigEntity> findByValue(String value, String language);



}
