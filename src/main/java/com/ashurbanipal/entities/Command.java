/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author swd
 */
@Entity
@Table(name = "command")
@NamedQueries({
    @NamedQuery(name = "Command.findAll", query = "SELECT c FROM Command c"),
    @NamedQuery(name = "Command.findByCommandId", query = "SELECT c FROM Command c WHERE c.commandId = :commandId"),
    @NamedQuery(name = "Command.findByCommandDate", query = "SELECT c FROM Command c WHERE c.commandDate = :commandDate")})
public class Command implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "command_id")
    private Integer commandId;
    @Column(name = "command_date")
    @Temporal(TemporalType.DATE)
    private Date commandDate;
    @JoinColumn(name = "address_id", referencedColumnName = "adress_id")
    @ManyToOne
    private Adress address;
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    @OneToOne
    private Cart cart;
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    @ManyToOne
    private User user;

    public Command() {
    }

    public Command(Integer commandId) {
        this.commandId = commandId;
    }

    public Integer getCommandId() {
        return commandId;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    public Date getCommandDate() {
        return commandDate;
    }

    public void setCommandDate(Date commandDate) {
        this.commandDate = commandDate;
    }

    public Adress getAddress() {
        return address;
    }

    public void setAddress(Adress address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commandId != null ? commandId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Command)) {
            return false;
        }
        Command other = (Command) object;
        if ((this.commandId == null && other.commandId != null) || (this.commandId != null && !this.commandId.equals(other.commandId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ashurbanipal.entities.Command[ commandId=" + commandId + " ]";
    }
    
}
