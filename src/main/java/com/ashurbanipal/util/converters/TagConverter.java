/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.util.converters;

import com.ashurbanipal.entities.Tag;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author swd
 */

@FacesConverter("TagConverter")
@ManagedBean
@RequestScoped
public class TagConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0){
            Tag tag = new Tag();
            tag.setLable(value.trim());
            return tag;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null)return ((Tag) value).getLable();
        return null;
    }
    
}
