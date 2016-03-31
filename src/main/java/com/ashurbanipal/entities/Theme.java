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

/**
 *
 * @author swd
 */
@Entity
@Table(name = "theme")
@NamedQueries({
    @NamedQuery(name = "Theme.findAll", query = "SELECT t FROM Theme t"),
    @NamedQuery(name = "Theme.findByThemeId", query = "SELECT t FROM Theme t WHERE t.themeId = :themeId"),
    @NamedQuery(name = "Theme.findByName", query = "SELECT t FROM Theme t WHERE t.name = :name"),
    @NamedQuery(name = "Theme.findByParent", query = "SELECT t FROM Theme t WHERE t.parentTheme = :parentTheme")
})
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "theme_id")
    private Integer themeId;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "theme")
    private List<Bookshelf> bookshelfList;
    @OneToMany(mappedBy = "theme")
    private List<Book> bookList;
    @OneToMany(mappedBy = "parentTheme")
    private List<Theme> themeList = new ArrayList<Theme>();
    @JoinColumn(name = "parent_theme_id", referencedColumnName = "theme_id")
    @ManyToOne
    private Theme parentTheme;

    public Theme() {
    }

    public Theme(Integer themeId) {
        this.themeId = themeId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bookshelf> getBookshelfList() {
        return bookshelfList;
    }

    public void setBookshelfList(List<Bookshelf> bookshelfList) {
        this.bookshelfList = bookshelfList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Theme> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<Theme> themeList) {
        this.themeList = themeList;
    }

    public Theme getParentTheme() {
        return parentTheme;
    }

    public void setParentTheme(Theme parentTheme) {
        this.parentTheme = parentTheme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (themeId != null ? themeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if ((this.themeId == null && other.themeId != null) || (this.themeId != null && !this.themeId.equals(other.themeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ashurbanipal.entities.Theme[ themeId=" + themeId + " ]";
    }
    
}
