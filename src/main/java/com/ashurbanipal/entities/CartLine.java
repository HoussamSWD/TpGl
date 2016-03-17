/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author swd
 */
@Entity
@Table(name = "cart_line")
@NamedQueries({
    @NamedQuery(name = "CartLine.findAll", query = "SELECT c FROM CartLine c"),
    @NamedQuery(name = "CartLine.findByCartLineId", query = "SELECT c FROM CartLine c WHERE c.cartLineId = :cartLineId"),
    @NamedQuery(name = "CartLine.findByQuantity", query = "SELECT c FROM CartLine c WHERE c.quantity = :quantity")})
public class CartLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cart_line_id")
    private Integer cartLineId;
    @Column(name = "quantity")
    private Integer quantity;
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    @ManyToOne
    private Cart cartId;
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    @ManyToOne
    private Book bookId;

    public CartLine() {
    }

    public CartLine(Integer cartLineId) {
        this.cartLineId = cartLineId;
    }

    public Integer getCartLineId() {
        return cartLineId;
    }

    public void setCartLineId(Integer cartLineId) {
        this.cartLineId = cartLineId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart getCartId() {
        return cartId;
    }

    public void setCartId(Cart cartId) {
        this.cartId = cartId;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartLineId != null ? cartLineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartLine)) {
            return false;
        }
        CartLine other = (CartLine) object;
        if ((this.cartLineId == null && other.cartLineId != null) || (this.cartLineId != null && !this.cartLineId.equals(other.cartLineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ashurbanipal.entities.CartLine[ cartLineId=" + cartLineId + " ]";
    }
    
}
