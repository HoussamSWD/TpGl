/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Book;
import com.ashurbanipal.entities.Cart;
import com.ashurbanipal.entities.CartLine;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

/**
 *
 * @author swd
 */
@Stateful
public class ChartController {
    
    public Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    @PostConstruct
    public void inti(){
        this.cart=new Cart();
    }
    
    public void addToChart(CartLine cartLine){
        //the relation from bothe sides
        this.cart.getCartLineList().add(cartLine);
        cartLine.setCart(cart);
        System.out.println("============================== a new book has been add");
    }
    
    public void addToChart(Book book){
        CartLine chartLine = new CartLine();
        chartLine.setBook(book);
        chartLine.setQuantity(1);
        addToChart(chartLine);
    }
    
    
}
