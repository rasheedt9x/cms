package com.sgdc.cms.dto;

/**
 * AuthResponse
 */
public class AuthResponse {

    private String token;
    private Object[] roles;


	public AuthResponse() {}
	public AuthResponse(String token) {
	    this.token = token;
	}

	
	public AuthResponse(String token,Object[] roles) {
	    this.token = token;
	    this.roles = roles;
	}


    public Object[] getRoles() {
		return roles;
	}
	public void setRoles(Object[] roles) {
		this.roles = roles;
	}
		public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
