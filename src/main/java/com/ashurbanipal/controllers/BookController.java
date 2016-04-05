/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Author;
import com.ashurbanipal.entities.Book;
import com.ashurbanipal.entities.Editor;
import com.ashurbanipal.entities.Tag;
import com.ashurbanipal.lazyModels.LazyBookDataModel;
import java.util.ArrayList;
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

    @PersistenceContext(unitName = "com.mycompany_TPGL_war_1.0-SNAPSHOTPU", type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @EJB
    CatalogueController catalogueController;

    /**
     * this function init the lazy data model; this instruction needs to be
     * executed in the postConstruct of the view the only reason that this
     * function exist is tho follow the mvc Model
     *
     * @param globalFilter the global filter of the table and it can be null;
     * @return a new LazyAuthorDataModel ready to use by the primefaces
     */
    public LazyBookDataModel setupLazyBookModel(String globalFilter) {
        LazyBookDataModel lazyModel = new LazyBookDataModel();
        lazyModel.setEm(em);
        lazyModel.setGlobalFilter(globalFilter);
        return lazyModel;
    }

    /*
       methods for doing the CRUD Operations on Books __________________________________________________
     */

    /**
     * this function search in the db for books with the same id (isbn) if there
     * isn't any it persist the book
     *
     * @param book
     * @throws Exception
     */
    public void addBook(Book book, ArrayList<String> tags) throws Exception {
        System.err.println("********************************** add book controller");

       
        // Hooking the relation from both sides 
        System.err.println("****************************** the relations");
         
        for (Author author : book.getAuthorList()) {
            System.err.println("the relation from the other side");
            author.getBookList().add(book);
        }
        ArrayList<Tag> managedTags = handelTags(tags);
        for (Tag managedTag : managedTags) {
            managedTag.getBookList().add(book);
            em.merge(managedTag);
        }
        book.setTagList(managedTags);
        try {
            System.err.println("****************************** the persist");
            
            for (Author author : book.getAuthorList()) {
                em.merge(author);
            }

            System.out.println("the last step the book" + book.getBookId());
            em.persist(book);
        } catch (Exception e) {

            System.err.println("err while persisting");
            throw e;
        }

        //}
    }

    public void deleteBook(Book book) throws ObjectNotFoundException {

        Book b = em.find(Book.class, book.getBookId());

        if (b != null) {
            em.remove(b);
        } else {
            throw new ObjectNotFoundException("Object Not Found");
        }

    }

    public void updateBook(Book book) {

        em.merge(book);

    }

    public List<Editor> getFiltredEditors(String filter, int maxResult) {
        return catalogueController.getFiltredEditors(filter, maxResult);
    }

    public List<Author> getFiltredAuthors(String filter, int maxResult) {
        return catalogueController.getFiltredAuthors(filter, maxResult);
    }

    public List<Tag> getFiltredTag(String filter, int maxResult) {
        TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.lable like :filter", Tag.class);
        query.setParameter("filter", "%" + filter.trim() + "%");
        query.setMaxResults(maxResult);
        return query.getResultList();

    }

    public Tag findTag(int id) {
        return em.find(Tag.class, id);
    }

    public ArrayList<Tag> handelTags(ArrayList<String> lables) {
        Tag tag = null;
        ArrayList<Tag> tags = new ArrayList<Tag>();
        for (String lable : lables) {
            TypedQuery<Tag> query = em.createNamedQuery("Tag.findByLable", Tag.class);
            query.setParameter("lable", lable);
            try {
                tag = query.getSingleResult();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("cant find the tag");
            }
            if(tag != null){
                tags.add(tag);
            }else{
                Tag t = new Tag();
                t.setLable(lable);
                em.persist(t);
                tags.add(t);
            }
        }
       return tags;

    }

}
