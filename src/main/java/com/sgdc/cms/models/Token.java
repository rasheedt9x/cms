package com.sgdc.cms.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Token
 */

@Entity
public class Token {

    @Id
    @Column(name = "token")
	String token;

    @Column(name = "is_blacklisted")
    boolean blacklisted;

    public Token() {}
	public Token(String token, boolean blacklisted) {
		this.token = token;
		this.blacklisted = blacklisted;
	}
	
	public boolean isBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
