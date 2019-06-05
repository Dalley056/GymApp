package com.myapp.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Indexed
@Table(name = "wo")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"woCreated", "woEdited"}, 
        allowGetters = true)
public class Wo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long woId;

    @NotBlank
    @Field
    private String woName;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date woCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date woEdited;
    
    @Field
    private String woEditedBy;
    
    public Wo() {
    	
    }
    
    public Wo(String name) {
    	this.woName = name;
    }

}
