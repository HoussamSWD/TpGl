/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.entities;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author benboubekeur
 */
@Entity
@Table(name = "adress", catalog = "Ashurbanipal", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adress.findAll", query = "SELECT a FROM Adress a"),
    @NamedQuery(name = "Adress.findByAdressId", query = "SELECT a FROM Adress a WHERE a.adressId = :adressId"),
    @NamedQuery(name = "Adress.findByStreet", query = "SELECT a FROM Adress a WHERE a.street = :street")})
public class Adress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "adress_id")
    private Integer adressId;
    @Size(max = 255)
    @Column(name = "street")
    private String street;
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "city", referencedColumnName = "city_id")
    @ManyToOne
    private City city;
    @JoinColumn(name = "country", referencedColumnName = "country_id")
    @ManyToOne
    private Country country;
    @OneToMany(mappedBy = "address")
    private List<User> userList = new ArrayList<User>();

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
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
        return "com.ashurbanipal.one.Adress[ adressId=" + adressId + " ]";
    }

}
