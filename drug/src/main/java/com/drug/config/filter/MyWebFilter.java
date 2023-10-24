package com.drug.config.filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "myWebFilter", urlPatterns="/*")
public class MyWebFilter extends OncePerRequestFilter {
	
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	    	 if("OPTIONS".equalsIgnoreCase(request.getMethod())){
		        	response.setStatus(HttpServletResponse.SC_OK);
		        	return;
		        }
	        filterChain.doFilter(request, response);
	    }
}
