/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Author;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author swd
 */
@ManagedBean
@RequestScoped
public class AddAuthor {

    /**
     * Creates a new instance of AddAuthor
     */
    
    String familyName;
    String firstName;
    String biography;
    
    @EJB
    CatalogueController catalogueController;
    
    public AddAuthor() {
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public void creatNewBook(){
        System.err.println("************ the new value"+this.familyName);
        Author a = new Author();
        a.setFamilyName(familyName);
        a.setFirstName(firstName);
        a.setBiography(biography);
        catalogueController.creatAuthor(a);
    }
    
    
}
