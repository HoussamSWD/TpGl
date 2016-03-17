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
@Table(name = "bookshelf")
@NamedQueries({
    @NamedQuery(name = "Bookshelf.findAll", query = "SELECT b FROM Bookshelf b"),
    @NamedQuery(name = "Bookshelf.findByBookshelfId", query = "SELECT b FROM Bookshelf b WHERE b.bookshelfId = :bookshelfId"),
    @NamedQuery(name = "Bookshelf.findByLabel", query = "SELECT b FROM Bookshelf b WHERE b.label = :label")})
public class Bookshelf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bookshelf_id")
    private Integer bookshelfId;
    @Size(max = 100)
    @Column(name = "label")
    private String label;
    @JoinColumn(name = "theme_id", referencedColumnName = "theme_id")
    @ManyToOne
    private Theme themeId;
    @OneToMany(mappedBy = "bookshelfId")
    private List<Book> bookList;

    public Bookshelf() {
    }

    public Bookshelf(Integer bookshelfId) {
        this.bookshelfId = bookshelfId;
    }

    public Integer getBookshelfId() {
        return bookshelfId;
    }

    public void setBookshelfId(Integer bookshelfId) {
        this.bookshelfId = bookshelfId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Theme getThemeId() {
        return themeId;
    }

    public void setThemeId(Theme themeId) {
        this.themeId = themeId;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookshelfId != null ? bookshelfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookshelf)) {
            return false;
        }
        Bookshelf other = (Bookshelf) object;
        if ((this.bookshelfId == null && other.bookshelfId != null) || (this.bookshelfId != null && !this.bookshelfId.equals(other.bookshelfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ashurbanipal.entities.Bookshelf[ bookshelfId=" + bookshelfId + " ]";
    }
    
}
