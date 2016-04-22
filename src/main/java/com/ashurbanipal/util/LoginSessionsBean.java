/*
 * The goal of this stateless is to :
 *      - get the current user session Object.
 *      - get the current user Request Object.
 *      - get the  user Id  of the user
 *      - get the  user name name of the user.
 */
package com.ashurbanipal.util;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author benboubekeur
 */
@Stateless
public class LoginSessionsBean implements Serializable {

    /**
     * Get the current Object Session
     */
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    /**
     * Get the Request Object
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    /**
     * Get the name of the authenticated current user
     */
    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    /**
     * Get the Id of the authenticated current user
     */
    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("userid");
        } else {
            return null;
        }
    }
}
