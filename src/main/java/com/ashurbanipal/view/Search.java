/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.BookController;
import com.ashurbanipal.entities.Book;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author swd
 */
@ManagedBean
@RequestScoped
public class Search {

    @EJB
    private BookController controller;
    
    
    
    private String keyWord;
    private List<Book> books;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    
    public Search() {
    }
    
    public String simpleSearch(){
        
        this.books = controller.simpleSearchForBooks(keyWord);
        return "searchResult";
    }
    
    public String addtochart(){
        System.out.println("*********************************** just calling a method ");
        return null;
    }
    
   
    
    
    
}
