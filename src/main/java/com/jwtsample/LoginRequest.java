package com.jwtsample;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String accessId;

    @NotBlank
    private String password;
    
    @NotBlank
    private String channelId;



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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
    
    
}
