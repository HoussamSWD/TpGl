/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.entities;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author swd
 */
@Entity
@Table(name = "adress")
@NamedQueries({
    @NamedQuery(name = "Adress.findAll", query = "SELECT a FROM Adress a"),
    @NamedQuery(name = "Adress.findByAdressId", query = "SELECT a FROM Adress a WHERE a.adressId = :adressId"),
    @NamedQuery(name = "Adress.findByCountry", query = "SELECT a FROM Adress a WHERE a.country = :country"),
    @NamedQuery(name = "Adress.findByCity", query = "SELECT a FROM Adress a WHERE a.city = :city"),
    @NamedQuery(name = "Adress.findByStreet", query = "SELECT a FROM Adress a WHERE a.street = :street")})
public class Adress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "adress_id")
    private Integer adressId;
    @Size(max = 40)
    @Column(name = "country")
    private String country;
    @Size(max = 40)
    @Column(name = "city")
    private String city;
    @Size(max = 255)
    @Column(name = "street")
    private String street;
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    @ManyToOne
    private User userId;
    @OneToMany(mappedBy = "addressId")
    private List<User> userList;
    @OneToMany(mappedBy = "addressId")
    private List<Command> commandList;

    public Adress() {
    }

    public Adress(Integer adressId) {
        this.adressId = adressId;
    }

    public Integer getAdressId() {
        return adressId;
    }

    public void setAdressId(Integer adressId) {
        this.adressId = adressId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adressId != null ? adressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adress)) {
            return false;
        }
        Adress other = (Adress) object;
        if ((this.adressId == null && other.adressId != null) || (this.adressId != null && !this.adressId.equals(other.adressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ashurbanipal.entities.Adress[ adressId=" + adressId + " ]";
    }
    
}
