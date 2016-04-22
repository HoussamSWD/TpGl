package com.ashurbanipal.util;

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
import javax.servlet.http.HttpSession;

/**
 *
 * @author benboubekeur
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            HttpServletRequest httpSertReqt = (HttpServletRequest) request;
            HttpServletResponse httpSertResp = (HttpServletResponse) response;
            HttpSession session = httpSertReqt.getSession(false);

            String reqURI = httpSertReqt.getRequestURI();
            if (reqURI.indexOf("/login.xhtml") >= 0
                    || (session != null && session.getAttribute("username") != null)
                    || reqURI.indexOf("/home.xhtml") >= 0
                    /**
                     * Returns true if and only if this string contains the
                     * specified sequence of char values.
                     */
                    || reqURI.contains("javax.faces.resource")
                    || reqURI.indexOf("/register.xhtml") >= 0) {
                chain.doFilter(request, response);
            } else {
                httpSertResp.sendRedirect(httpSertReqt.getContextPath() + "/register.xhtml");
            }
        } catch (Exception e) {
            System.out.println(" " + e.getMessage());

        }

    }

    @Override
    public void destroy() {
    }

}