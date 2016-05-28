/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.BookController;
import com.ashurbanipal.entities.Book;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author swd
 */
@ManagedBean
@RequestScoped
public class BooksDetails {

    /**
     * Creates a new instance of BooksDetails
     */
    
    @EJB
    BookController controller;
    
    String bookId;
    Book book ;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    
    
    public BooksDetails() {
        
    }
    
    @PostConstruct
    public void init(){
        
        if (bookId == null) System.out.println("============== not yet pased ");
        else System.err.println("=================== yes it's pased :" + bookId);
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String param = parameterMap.get("bookId");
        
        System.out.println("================= book id " + param);
        
        this.book = controller.findBook(param);
    }
    
}
