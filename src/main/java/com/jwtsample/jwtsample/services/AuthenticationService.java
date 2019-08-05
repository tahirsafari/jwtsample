package com.jwtsample.jwtsample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.safari.pg.cbs.def.CbsConstants;
import com.safari.pg.cbs.def._UserAuthInfo;
import com.safari.pg.cbsint.CbsAgent;
import com.safari.pg.cbsint.CbsAuthInterface;

@Service
public class AuthenticationService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	CbsAgent ca;
	CbsAuthInterface chInterface;
	
	public _UserAuthInfo authenticate(String accessId, String password, int channelId) throws Exception {
		this.ca = new CbsAgent(jdbcTemplate.getDataSource().getConnection());
    	this.chInterface = new  CbsAuthInterface(this.ca);

		int authType = CbsConstants.AUTHTYPE_USERID_ACCESSKEY;
	
		_UserAuthInfo  user = chInterface.authenticate(accessId, password, channelId, authType);
		System.out.println("result "+user.toString());
		return user;

	}
	
	public _UserAuthInfo getUserInfoByUserId(Long userId) throws Exception {
		this.ca = new CbsAgent(jdbcTemplate.getDataSource().getConnection());
    	this.chInterface = new  CbsAuthInterface(this.ca);
//		String accessId = "1000008";
    	String password = "APIXTOIWEHSDLKOWERH";
		int channelId = 0;
		int authType = CbsConstants.AUTHTYPE_USERID_ACCESSKEY;
	
		_UserAuthInfo  user = chInterface.authenticate(userId.toString(), password, channelId, authType);
		System.out.println("result "+user.toString());
		return user;

	}
}
