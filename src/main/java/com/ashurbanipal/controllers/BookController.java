/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Author;
import com.ashurbanipal.entities.Book;
import com.ashurbanipal.entities.Editor;
import com.ashurbanipal.lazyModels.LazyBookDataModel;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

/**
 *
 * @author swd
 */
@Stateful
public class BookController {
 
    @PersistenceContext(unitName = "com.mycompany_TPGL_war_1.0-SNAPSHOTPU",type = PersistenceContextType.EXTENDED)
    EntityManager em ;
    
    @EJB
    CatalogueController catalogueController;
    
    /**
     *  this function init the lazy data model; this instruction needs to be
     * executed in the postConstruct of the view 
     * the only reason that this function exist is tho follow the mvc Model
     * @param globalFilter the global filter of the table and it can be null;
     * @return a new LazyAuthorDataModel ready to use by the primefaces
     */
    public LazyBookDataModel setupLazyBookModel(String globalFilter){        
        LazyBookDataModel lazyModel = new LazyBookDataModel();
        lazyModel.setEm(em);
        lazyModel.setGlobalFilter(globalFilter);
        return lazyModel;
    }
    /*
       methods for doing the CRUD Operations on Books __________________________________________________
    */
    
    /**
     *  this function search in the db for books with the same id (isbn) if
     *  there isn't any it persist the book 
     * @param book 
     * @throws Exception
     */
    public void addBook(Book book) throws  Exception{
        System.err.println("********************************** add book controller");
        
        /*
        TypedQuery<Book> query = em.createNamedQuery("Book.findByBookId", Book.class);
        query.setParameter(":bookId", book.getBookId());
        List<Book> resullt = query.getResultList();
        if( ! resullt.isEmpty())
            throw  new Exception("Book With the same ISBN already existe");
        else{*/
            // Hooking the relation from both sides 
            
            System.err.println("****************************** the relations");
//            book.getEditor().getBookList().add(book);
//            
            for (Author author : book.getAuthorList()) {
                System.err.println("the relation from the other side");
                author.getBookList().add(book);                
            }
            try {
                System.err.println("****************************** the persist");
               // em.merge(book.getEditor());
               for(Author author : book.getAuthorList())em.merge(author);
                em.persist(book);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
        //}
    }
    public void deleteBook(Book book) throws ObjectNotFoundException{
        
        Book b = em.find(Book.class, book);
        
        if(b!=null)
          em.remove(b);
        else
            throw  new ObjectNotFoundException("Object Not Found");
        
    }
    public void updateBook(Book book){
        
        em.merge(book);
        
    }
    
    public List<Editor> getFiltredEditors(String filter,int maxResult){
        return catalogueController.getFiltredEditors(filter, maxResult);
    }
    
    public List<Author> getFiltredAuthors(String filter, int maxResult){
        return catalogueController.getFiltredAuthors(filter, maxResult);
    }
    
}
