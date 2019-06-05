package com.myapp.payload;

public class UserSummary {
	private Long id;
	private String email;
	private String forename;
	
	public UserSummary(Long id, String email, String forename) {
		this.id = id;
		this.email = email;
		this.forename = forename;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

}
