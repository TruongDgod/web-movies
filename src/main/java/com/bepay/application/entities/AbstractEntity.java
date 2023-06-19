package com.bepay.application.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class AbstractEntity {

    @Column(name="IS_DELETED")
    private Integer isDeleted;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Column(name="UPDATED_BY")
    private String updatedBy;

    @Column(name="UPDATED_DATE")
    private Date updatedDate;

}
