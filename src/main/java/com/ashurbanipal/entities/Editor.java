package com.ashurbanipal.entities;



import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "editor")
@NamedQueries({
    @NamedQuery(name = "Editor.findAll", query = "SELECT e FROM Editor e"),
    @NamedQuery(name = "Editor.findByEditorId", query = "SELECT e FROM Editor e WHERE e.editorId = :editorId"),
    @NamedQuery(name = "Editor.findByName", query = "SELECT e FROM Editor e WHERE e.name = :name"),
    @NamedQuery(name = "Editor.findByContry", query = "SELECT e FROM Editor e WHERE e.contry = :contry")})
public class Editor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "editor_id")
    private Integer editorId;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "contry")
    private String contry;
    @OneToMany(mappedBy = "editor")
    private List<Book> bookList;

    public Editor() {
    }

    public Editor(Integer editorId) {
        this.editorId = editorId;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
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
        hash += (editorId != null ? editorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Editor)) {
            return false;
        }
        Editor other = (Editor) object;
        if ((this.editorId == null && other.editorId != null) || (this.editorId != null && !this.editorId.equals(other.editorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.dependancytest.util.Editor[ editorId=" + editorId + " ]";
    }
    
}
