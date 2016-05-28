/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.BookController;
import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Author;
import com.ashurbanipal.entities.Book;
import com.ashurbanipal.entities.Editor;
import com.ashurbanipal.entities.Tag;
import com.ashurbanipal.lazyModels.LazyBookDataModel;
import com.ashurbanipal.util.UploadeUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author swd
 */
@ManagedBean
@ViewScoped
public class BooksManag implements Serializable {

    public static final String DESTINATION  = "images/books/";
    
    List<Editor> editors = new ArrayList<Editor>();
    List<String> languages = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();

    private UploadedFile cover;
    

    @EJB
    private BookController controller;

    @EJB
    CatalogueController catalogueController;

    private LazyBookDataModel books;

    private String globalFilter;

    private Book selectedBook;

    /**
     * Creates a new instance of BooksManag
     */
    public BooksManag() {

    }

    @PostConstruct
    public void init() {
        books = controller.setupLazyBookModel(globalFilter);
        languages.add("English");
        languages.add("العربية");
        languages.add("françai");

    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public void prepareNew() {
        System.err.println("preparint the new add");
        selectedBook = new Book();

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", "dqfqdg");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addBook() {

        System.out.println(" ************** add book view ***************************");
        if (selectedBook != null) {
            System.err.println("the book is not null");
        }
        if (selectedBook.getEditor() != null) {
            System.err.println("the editor is not null");
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
//        try {
//            System.out.println("com.ashurbanipal.view.BooksManag.addBook() __________no null");
//            UploadeUtils.copyFile("test.jpg", cover.getInputstream(), DESTINATION);
//            
//            System.out.println("com.ashurbanipal.view.BooksManag.addBook() __________no null");
//        } catch (IOException ex) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can't Upload the cover"));
//            return;
//        }
        
        try {
            controller.addBook(selectedBook, tags);
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can't create new Book"));
            return;
        }
        if(cover != null)context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "file uploaded" + cover.getFileName()));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New Book successufuly created"));
    }

    public void deleteBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            controller.deleteBook(selectedBook);
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can't delete this Book"));
            return;
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The Book successufuly deleted"));
    }

    public void editBook(Book book) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            controller.updateBook(book);
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can't update this Book"));
            return;
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The Book successufuly updated"));
    }

    public List<Editor> completeEditors(String query) {
        List<Editor> results = controller.getFiltredEditors(query, 10);
        return results;
    }

    public List<Author> completeAuthors(String query) {
        return controller.getFiltredAuthors(query, 10);
    }

    public List<Tag> completeTag(String query) {
        return controller.getFiltredTag(query, 10);
    }

    

    public LazyBookDataModel getBooks() {
        return books;
    }

    public void setBooks(LazyBookDataModel books) {
        this.books = books;
    }

    public String getGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(String globalFilter) {
        this.globalFilter = globalFilter;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public UploadedFile getCover() {
        return cover;
    }

    public void setCover(UploadedFile cover) {
        this.cover = cover;
    }
    
    

}
