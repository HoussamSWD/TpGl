/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.LoginController;
import com.ashurbanipal.util.LoginSessionsBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author benboubekeur
 */
@ManagedBean
@SessionScoped
public class LoginManag {

    @EJB
    LoginController loginController;

    private String username = null;
    private String password = null;
    private String salt = null;
    private boolean valid = false;

    /**
     * Creates a new instance of LoginMang
     */
    public LoginManag() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void validate() throws Exception {
        System.err.println("from Validate() LoginManag");

        //Calling the validate method from LoginController 
        valid = loginController.validate(username, password);

        //Declaring new FacesMessage to be send it back to Client 
        FacesMessage message = null;
        RequestContext context = RequestContext.getCurrentInstance();

        if (valid) {
            System.err.println("A valide USER ******************* " + username + " is valid :" + valid);

            //If the user is a valid user than we create a new session + put a username attribute
            HttpSession session = LoginSessionsBean.getSession();
            session.setAttribute("username", username);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);

        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Incorrect Username and Passowrd",
                    "Please enter correct username and Password");

        }

        FacesContext.getCurrentInstance().addMessage(null, message);

        //Adding new parameter to the response so it can be processed By javascript
        context.addCallbackParam("loggedIn", valid);
    }

    //logout event, invalidate session
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("Logout has been called****************");
        HttpSession session = LoginSessionsBean.getSession();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inf", "LogOut"));
        session.invalidate();
        return "";

    }

    public void test() {
        System.out.println("From test ");
    }
}
