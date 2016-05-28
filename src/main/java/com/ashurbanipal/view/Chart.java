/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.view;

import com.ashurbanipal.controllers.BookController;
import com.ashurbanipal.controllers.ChartController;
import com.ashurbanipal.entities.Book;
import com.ashurbanipal.entities.Cart;
import com.ashurbanipal.entities.CartLine;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author swd
 */
@ManagedBean
@SessionScoped
public class Chart implements Serializable{
    
//    @EJB
//    private BookController bookController;
    
    public Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
//    @PostConstruct
//    public void inti(){
//        System.err.println("\n==\n==\n==\n==\n==\n==\n==\n================= init the chart");
//        this.cart=new Cart();
//    }
    
    
    
//    public void addToChart(String bookid){
//        System.out.println("=\n=\n==\n==\n==\n==\n==\n==\n==\n==\n============ a\n new book has been add");
//        Book book  = bookController.findBook(bookid);
//        if(book != null){
//            CartLine chartLine = new CartLine();
//            chartLine.setBook(book);
//            chartLine.setQuantity(1);
//            this.cart.getCartLineList().add(chartLine);
//            chartLine.setCart(cart);
//            System.out.println("============================== a new book has been add");
//        }
//    }
    
    
    
}
