/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.util.converters;

import com.ashurbanipal.controllers.LoginController;
import com.ashurbanipal.entities.Country;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author benboubekeur
 */
@FacesConverter("countryConverter")
@ManagedBean
@RequestScoped
public class CountryConverter implements Converter {

    /**
     * Creates a new instance of CountryConverter
     */
    @EJB
    LoginController loginController;

    public CountryConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            Country country = loginController.findCountry(value);
            return country;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            return ((Country) value).getCountryId();
        }
        return null;

    }

}
