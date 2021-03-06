/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.util.converters;

import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Editor;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author swd
 */

@FacesConverter("editorConverter")
@ManagedBean
@RequestScoped
public class EditorConverter implements Converter{
    
    @EJB
    CatalogueController controller;
    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0){
            try {
                Editor editor= controller.findEditor(Integer.parseInt(value));
                return editor;
            } catch (Exception e) {
                System.err.println("Errror");
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Editor name"));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null)return ((Editor) value).getEditorId().toString();
        return null;
    }
    
}
