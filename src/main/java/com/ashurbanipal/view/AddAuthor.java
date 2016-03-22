/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Author;
import com.ashurbanipal.lazyModels.LazyAuthorDataModel;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.resource.spi.LazyAssociatableConnectionManager;
import static org.primefaces.behavior.confirm.ConfirmBehavior.PropertyKeys.message;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author swd
 */
@ManagedBean
@ViewScoped
public class AddAuthor implements Serializable{

    /**
     * Creates a new instance of AddAuthor
     */
    
//    String familyName;
//    String firstName;
//    String biography = "";
    
    Author selectedAuthor = null;
    String globalFilter;
    
    @EJB
    CatalogueController catalogueController;
    
    @PersistenceContext(unitName = "com.mycompany_TPGL_war_1.0-SNAPSHOTPU")
    EntityManager em;
   
    
    LazyAuthorDataModel autors ;
    
    public AddAuthor() {
    }
    
    @PostConstruct
    public void init(){
        System.out.println("******************************************** step 0");
        this.autors = new LazyAuthorDataModel();
        autors.setEm(em);
        autors.setGlobalFilter(globalFilter);
    }

    public String getGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(String globalFilter) {
        System.out.println("**** the filter changed");
        this.globalFilter = globalFilter;
    }

    public Author getSelectedAuthor() {
        return selectedAuthor;
    }

    public void setSelectedAuthor(Author selectedAuthor) {
        this.selectedAuthor = selectedAuthor;
    }
    
    
//    public String getFamilyName() {
//        return familyName;
//    }
//
//    public void setFamilyName(String familyName) {
//        this.familyName = familyName;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getBiography() {
//        return biography;
//    }
//
//    public void setBiography(String biography) {
//        this.biography = biography;
//    }

    public LazyAuthorDataModel getAutors() {
        return autors;
    }
    
    public void preparCreate(){
        this.selectedAuthor = new Author();
    }

    public void setAutors(LazyAuthorDataModel autors) {
        this.autors = autors;
    }
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", ((Author) event.getObject()).getFamilyName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void creatNewBook() throws Exception{
//        System.err.println("************ the new value"+this.familyName);
//        
//        //create an author and fill it 
//        Author a = new Author();
//        a.setFamilyName(familyName);
//        a.setFirstName(firstName);
//        a.setBiography(biography);
        
        //initiate the context to output a messeg
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            catalogueController.creatAuthor(selectedAuthor);
        }catch(Exception e){
            context.addMessage("family_name" , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",  e.getMessage()) );
            return;
        }
        
        //if every thing is good we show a success message
        context.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful",  "The author" + selectedAuthor.getFirstName() + "is created") );
        
    }
    
    public void editAuthor(){
        catalogueController.editAuthor(selectedAuthor);
        
        //if every thing is good we show a success message
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful",  "The author" + selectedAuthor.getFirstName() + "is updated") );
        
    }
    
    public void deleteAuthor(){
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            catalogueController.deleteAuthor(selectedAuthor);
        } catch (ObjectNotFoundException ex) {
            context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
        }
        //if every thing is good we show a success message
        
        context.addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful",  "The author" + selectedAuthor.getFirstName() + "is deleteds") );
        
    }
    
    
}
