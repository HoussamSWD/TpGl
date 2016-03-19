/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Author;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
    public void creatAuthor(Author author){
        em.persist(author);
    }
}
