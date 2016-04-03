/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.util.converters;

import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Author;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author swd
 */

@FacesConverter("authorConverter")
@ManagedBean
@SessionScoped
public class AuthorConverter implements Converter{

    @EJB
    CatalogueController controller;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0){
            try {
                return controller.findAuthor(Integer.parseInt(value));
            } catch (Exception e) {
                System.err.println("Converter error");
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Author"));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) return ((Author) value).getAuthorId().toString();
        return null;
    }
    
}
