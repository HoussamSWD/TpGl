/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Author;
import com.ashurbanipal.entities.Theme;
import com.ashurbanipal.lazyModels.LazyAuthorDataModel;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author swd
 */
@Stateless
public class CatalogueController {

    
    
    @PersistenceContext(unitName = "com.mycompany_TPGL_war_1.0-SNAPSHOTPU")
    EntityManager em;
    
    /**
     *  this function init the lazy data model; this instruction needs to be
     * executed in the postConstruct of the view 
     * the only reason that this function exist is tho follow the mvc Model
     * @param globalFilter the global filter of the table and it can be null;
     * @return a new LazyAuthorDataModel ready to use by the primefaces
     */
    public LazyAuthorDataModel setupLazyAhthorModel(String globalFilter){
        
        LazyAuthorDataModel authorsModel = new LazyAuthorDataModel();
        //the lazy model needs an entity manager to preform several tasks
        authorsModel.setEm(this.em);
        authorsModel.setGlobalFilter(globalFilter);
        return authorsModel;
    }
    
    /**
     * this function search in the db for author withe the same family and first
     * name if thir is no such author it pesist it 
     * @param author author to add to the data base 
     * @throws Exception the author alrady exist 
     */
    public void creatAuthor(Author author) throws Exception{
        
        //search if this author alrady exist
        TypedQuery<Author> query = em.createQuery("SELECT a "
                + "FROM Author a "
                + "WHERE a.familyName = :familyName "
                + "and a.firstName = :firstName",Author.class);
        query.setParameter("familyName", author.getFamilyName());
        query.setParameter("firstName", author.getFirstName());
        
        List<Author> authors = query.getResultList();
        //if it exist throw an exeption
        if(authors.size() > 0)throw new Exception("This author alrady exist");
        
        // if it don't exist then create it 
        em.persist(author);
    }
    
    /**
     * persist the modifications of an author
     * u can't modify the id
     * @param author the modified author
     */
    public void editAuthor(Author author) throws Exception{
        try{
        em.merge(author);
        }catch(Exception ex){
            throw ex;
        }
    }
    
    /**
     *  delete an author from the database if exist
     * @param author the author to delete
     * @throws ObjectNotFoundException in case the author don't exist
     */
    public void deleteAuthor(Author author) throws ObjectNotFoundException{
        Author a = em.find(Author.class,author.getAuthorId() );
        
        if(a != null)
            em.remove(a);
        else throw new ObjectNotFoundException("Author to delete not found");
    }
    
    /*
     ontroleur pour gerer les themes 
    */
    
    /**
     * add new theme to the db if not already exist
     * @param theme theme to add
     * @throws Exception if the theme exist
     */
    public void createTheme(Theme theme) throws Exception{
        TypedQuery<Theme> query = em.createNamedQuery("Theme.findByName",Theme.class);
        query.setParameter("name", theme.getName());
        List<Theme> existed =  query.getResultList();
        if(!existed.isEmpty()) em.persist(theme);
        else throw  new Exception("the theme alrady exist");
        
    }
    
    /**
     * delete a theme from the db
     * @param theme to delete
     * @throws Exception if the theme not found
     */
    public void deleteTheme(Theme theme) throws Exception{
        try {
            em.remove(theme);
        } catch (Exception e) {
            throw e;
        }
        
    }

    /**
     * update the theme 
     * @param theme theme with the new values
     * @throws Exception if something went wrong
     */
    public void editTheme(Theme theme) throws Exception{
        try {
            em.merge(theme);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * find the children of an existent theme 
     * if the them is null return the roots(main) themes
     * @param theme theme to get  children
     * @return the children of a given theme
     */
    public List<Theme> getThemes(Theme theme){
        
        if(theme == null){
            TypedQuery<Theme> query = em.createQuery("SELECT t from Theme t WHERE t.parentTheme IS NULL",Theme.class);
            return query.getResultList();
        }else{
            TypedQuery<Theme> query = em.createNamedQuery("Theme.findByParent",Theme.class);
            query.setParameter("parentTheme", theme);
            return query.getResultList();
        }
    }
}
