/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.BookController;
import com.ashurbanipal.controllers.CatalogueController;
import com.ashurbanipal.entities.Book;
import com.ashurbanipal.entities.Editor;
import com.ashurbanipal.lazyModels.LazyBookDataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author swd
 */
@ManagedBean
@ViewScoped
public class BooksManag implements Serializable{

    Editor editor;
    List<Editor> editors = new ArrayList<Editor>();

    List<String> languages = new ArrayList<String>();

    public void submit(){
        System.out.println("Hello From  submiyt**************************************");
        System.out.println("Editor :"+ editor);
    }
    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

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
        editors = catalogueController.getAllEditors();
        
        
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
        if(selectedBook != null)System.err.println("the book is not null");
        if(selectedBook.getEditor() != null)System.err.println("the editor is not null");
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            controller.addBook(selectedBook);
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can't create new Book"));
            return;
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New Book successufuly created"));
    }

    public void deleteBook(Book book) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            controller.deleteBook(book);
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

    public List<Editor> complete(String query) {
        List<Editor> results = controller.getFiltredEditors(query, 10);
        System.out.println("auto complete___________________________");
        return results;
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

}
