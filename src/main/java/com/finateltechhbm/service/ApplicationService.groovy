package com.finateltechhbm.service

import groovy.util.logging.Slf4j;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class ApplicationService {
	
	public void initTracking(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("\nauthentication name : "+auth.getPrincipal()+""+auth.getDetails().getSessionId());
		MDC.put("SID",   String.format("%s -IP %s -SessionID %s", auth.getPrincipal(),auth.getDetails().getRemoteAddress(),((Object) auth.getDetails()).getSessionId()));
	}
	
}
