/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Author;
import com.ashurbanipal.lazyModels.LazyAuthorDataModel;
import java.util.List;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    public void editAuthor(Author author){
        em.merge(author);
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
    
}
