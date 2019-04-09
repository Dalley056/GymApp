package com.myapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"userCreated", "userEdited"}, 
        allowGetters = true)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Field
    @Column(name = "user_email")
    private String username;

    @Field
    private String userForename;
    
    @Field
    private String userSurname;
    
    @Field
    private String userMiddleName;
    
    @Field
    private String userPassword;
    
    private int userFlags;
    
    private char userDeleted;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date userCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date userEdited;
    
    @Field
    private String userEditedBy;
    
    @Transient
    private String passwordConfirm;
    
    public User() {}
    
    public User(Long id, String email, String forename, String surname, String middleName, String userPassword) {
    	this.userId = id;
    	this.username = email;
    	this.userForename = forename;
    	this.userSurname = surname;
    	this.userMiddleName = middleName;
    	this.userPassword = userPassword;
    }
    
    public User(String email, String forename, String surname, String middleName, String userPassword) {
    	this.username = email;
    	this.userForename = forename;
    	this.userSurname = surname;
    	this.userMiddleName = middleName;
    	this.userPassword = userPassword;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userEmail) {
		this.username = userEmail;
	}

	public String getUserForename() {
		return userForename;
	}

	public void setUserForename(String userForename) {
		this.userForename = userForename;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getUserMiddleName() {
		return userMiddleName;
	}

	public void setUserMiddleName(String userMidleName) {
		this.userMiddleName = userMidleName;
	}

	public int getUserFlags() {
		return userFlags;
	}

	public void setUserFlags(int userFlags) {
		this.userFlags = userFlags;
	}

	public char getUserDeleted() {
		return userDeleted;
	}

	public void setUserDeleted(char userDeleted) {
		this.userDeleted = userDeleted;
	}

	public Date getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(Date userCreated) {
		this.userCreated = userCreated;
	}

	public Date getUserEdited() {
		return userEdited;
	}

	public void setUserEdited(Date userEdited) {
		this.userEdited = userEdited;
	}

	public String getUserEditedBy() {
		return userEditedBy;
	}

	public void setUserEditedBy(String userEditedBy) {
		this.userEditedBy = userEditedBy;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
}
