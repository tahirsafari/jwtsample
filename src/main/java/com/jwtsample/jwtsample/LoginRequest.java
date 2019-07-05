package com.jwtsample.jwtsample;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String accessId;

    @NotBlank
    private String password;



    public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
