package com.myapp.models;

import java.io.Serializable;
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
@Table(name = "exercises")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exercise implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseId;

    @NotBlank
    @Field
    private String exerciseName;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date exerciseCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date exerciseEdited;
    
    @Field
    private String exerciseEditedBy;
    
    public Exercise() {
    	
    }
    
    public Exercise(String eName, User user) {
    	this.user = user;
    	this.exerciseName = eName;
    }
    
    public Exercise(String name) {
    	this.exerciseName = name;
    }

	public Long getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExerciseCreated() {
		return exerciseCreated;
	}

	public void setExerciseCreated(Date exerciseCreated) {
		this.exerciseCreated = exerciseCreated;
	}

	public Date getExerciseEdited() {
		return exerciseEdited;
	}

	public void setExerciseEdited(Date exerciseEdited) {
		this.exerciseEdited = exerciseEdited;
	}

	public String getExerciseEditedBy() {
		return exerciseEditedBy;
	}

	public void setExerciseEditedBy(String exerciseEditedBy) {
		this.exerciseEditedBy = exerciseEditedBy;
	}

}
