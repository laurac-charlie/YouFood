package com.supinfo.youfood.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.youfood.entity.Administrator;

/**
 * Servlet Filter implementation class AuthentificateFilter
 */
@WebFilter({"/users/*","/productType/*"})
public class AuthentificateFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthentificateFilter() {
        
    }

    private FilterConfig config;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
    
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			
			if(httpServletRequest.getSession().getAttribute("loggedUser") == null || !(httpServletRequest.getSession().getAttribute("loggedUser") instanceof Administrator)) 
			{
				httpServletResponse.sendRedirect(config.getServletContext().getContextPath() + "/login");
				return;
			}
		}
		chain.doFilter(request, response);
	}


}
