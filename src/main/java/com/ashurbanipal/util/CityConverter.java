/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.util;

import com.ashurbanipal.controllers.LoginController;
import com.ashurbanipal.entities.City;
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
@FacesConverter("cityConverter")
@ManagedBean
@RequestScoped
public class CityConverter implements Converter {

    @EJB
    LoginController loginController;

    /**
     * Creates a new instance of CityConverter
     */
    public CityConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            City city = loginController.findCity(value);
            return city;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            return ((City) value).getCityId().toString();
        }
        return null;

    }

}
