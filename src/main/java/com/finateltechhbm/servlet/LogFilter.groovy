package com.finateltechhbm.servlet;

import groovy.util.logging.Slf4j;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
@WebFilter(filterName = "logFilter")
@Slf4j
public class LogFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		try {
            SecurityContext context = SecurityContextHolder.getContext();
            if (context != null) {
                Authentication authentication = context.getAuthentication();
                Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
                if (authentication != null) {
                    def userId =   authentication.getPrincipal();
					def SessionId
					def IpAddress
					if(auth.getDetails() != null){
						SessionId =  auth.getDetails().getSessionId(); 
						IpAddress = auth.getDetails().getRemoteAddress();
					}  
                    MDC.put("SID", String.format("User - %s ; IP -%s ; SessionId -%s  ", userId,IpAddress,SessionId   )); 
                }
            }
        } catch (Throwable e) {
            log.error("Error trace in Log Filter", e.getMessage());
        } 
        chain.doFilter(req, res);
        MDC.clear();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
