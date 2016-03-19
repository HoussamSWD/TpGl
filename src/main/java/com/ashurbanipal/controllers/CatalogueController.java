/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Author;
import java.util.List;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "com.mycompany_TPGL_war_1.0-SNAPSHOTPU")
    EntityManager em;
    
    /**
     *
     * @param author
     * @throws Exception
     */
    public void creatAuthor(Author author) throws Exception{
        
        //search if this author alrady exist
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.familyName = :familyName and a.firstName = :firstName",Author.class);
        query.setParameter("familyName", author.getFamilyName());
        query.setParameter("firstName", author.getFirstName());
        
        System.err.println("*************no pb hnaaaaaaaaaaaaaa");
        List<Author> authors = query.getResultList();
        //if it exist throw an exeption
        if(authors.size() > 0)throw new Exception("This author alrady exist");
        
        // if it don't exist then create it 
        em.persist(author);
    }
}
