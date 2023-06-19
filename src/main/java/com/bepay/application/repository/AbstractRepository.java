package com.bepay.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vidal
 * @return
 * @see AbstractRepository
 */
@NoRepositoryBean
public interface AbstractRepository<E, ID extends Serializable> extends JpaRepository<E, ID> {
    @Query(value = "SELECT CURRENT_DATE FROM DUAL", nativeQuery = true)
    Date getCurrentDate();
}
