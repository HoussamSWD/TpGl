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
    @PersistenceContext(unitName = "com.mycompany_TPGL_war_1.0-SNAPSHOTPU")
    EntityManager em;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0){
            try {
                System.err.println("*************** p" + value);
                if(controller == null)System.err.println("the controller is not enjected");
                if(em == null)System.err.println("the entity manager is not engected");
                
                int a = Integer.parseInt(value);
                System.err.println("a =============================== " + a);
                Editor editor= controller.findEditor(a);
                System.out.println("the editor name :) " + editor.getName());
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
        System.err.println("******************" + ((Editor) value));
        return null;
    }
    
}
