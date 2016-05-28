/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Theme;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author swd
 */
@ManagedBean
@RequestScoped
public class home {

    private List<String> images;
    List<Theme> themes;
    
    @EJB
    private CatalogueController controller;
    
    
    public home() {
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }
    
    
    
    @PostConstruct
    public void init(){
        images = new ArrayList<String>();
        for(int i = 1; i< 7; i++){
            this.images.add("book" + i + ".jpg");
            System.err.println("book"+i+".jpg");
        }
        
        this.themes = controller.getThemes();
    }

    public List<String> getImages() {
        return images;
    }
    
    
    
}
