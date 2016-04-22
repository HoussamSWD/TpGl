/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.LoginController;
import com.ashurbanipal.entities.Adress;
import com.ashurbanipal.entities.City;
import com.ashurbanipal.entities.Country;
import com.ashurbanipal.entities.User;
import com.ashurbanipal.util.LoginSessionsBean;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author benboubekeur
 */
@ManagedBean
@ViewScoped
public class UserRegistartion {

    /**
     * Creates a new instance of UsersManag
     */
    @EJB
    LoginController loginController;
    
    private String cou = null;
    private User user = new User();
    
    private boolean submited;
    
    private List<Country> countries = new ArrayList<Country>();
    
    private List<String> algeria = new ArrayList<String>();
    private List<String> moroco = new ArrayList<String>();
    private List<String> usa = new ArrayList<String>();
    private City city = null;
    private Country selectedCountry = null;
    
    public UserRegistartion() {
    }
    
    @PostConstruct
    public void init() {
        countries = loginController.getAllCountries();
    }
    
    public City getCity() {
        return city;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean isSubmited() {
        return submited;
    }
    
    public void setSubmited(boolean submited) {
        this.submited = submited;
    }
    
    public Country getSelectedCountry() {
        return selectedCountry;
    }
    
    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }
    
    public List<Country> getCountries() {
        return countries;
    }
    
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
    
    public String addUser() throws NoSuchAlgorithmException {
        
        Adress adress = user.getAddress();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            //adress.setCity(city);
            //adress.setCountry(selectedCountry);
            //user.setAddress(adress);

            loginController.addUser(user);
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error /****************" + ex.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can't Add new User"));
            return "";
        } catch (Exception exception) {
            System.out.println("Error /****************" + exception.getMessage());
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This User already existe"));
            return "";
        }
        HttpSession session = LoginSessionsBean.getSession();
        session.setAttribute("username", user.getEmail());
        System.err.println("The user name : " + session.getAttribute("username"));
        return "home";
    }
    
    public void onCountryChange() {
        if (selectedCountry != null) {
            
        }
    }
}
