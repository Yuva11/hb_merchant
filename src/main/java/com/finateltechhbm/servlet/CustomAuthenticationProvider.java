package com.finateltechhbm.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.finateltechhbm.dto.GroupUserDTO;
import com.finateltechhbm.dto.LoginRequest;
import com.finateltechhbm.service.RestService;
import com.finateltechhbm.ui.DealBean;
import com.finateltechhbm.validation.*;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
	@Autowired
	RestService restservice; 
	
	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    
	    LoginRequest request = new LoginRequest();
	    GroupUserDTO profileData = new GroupUserDTO();
		Boolean loginStatus = false;
		String name = authentication.getName();
		String password = authentication.getCredentials().toString(); 
		log.info("name: "+name);
		request.setUserName(name);
        request.setPassWord(password);
        if (name == null || password == null || name.isEmpty() || password.isEmpty())
            throw new BadCredentialsException("INVALID");
		try {
			profileData = restservice.auth(request);
		} catch (Exception e) {
			log.error("Exception in Authentication" , e);
			throw new BadCredentialsException("INVALID");
		}
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_MERCHANT");
		grantedAuths.add(authority);
		if(profileData != null){
		    log.info("User Authentication sucessfull[" + name+"]");
			Authentication auth = new UsernamePasswordAuthenticationToken(profileData, password, grantedAuths);
			return auth;
		}
		else {
		    log.error("User Authentication failed");
		    throw new BadCredentialsException("INVALID");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
