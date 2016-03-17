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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "groupe")
@NamedQueries({
    @NamedQuery(name = "Groupe.findAll", query = "SELECT g FROM Groupe g"),
    @NamedQuery(name = "Groupe.findByGroupeId", query = "SELECT g FROM Groupe g WHERE g.groupeId = :groupeId"),
    @NamedQuery(name = "Groupe.findByLabel", query = "SELECT g FROM Groupe g WHERE g.label = :label")})
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "groupe_id")
    private Integer groupeId;
    @Size(max = 40)
    @Column(name = "label")
    private String label;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @JoinTable(name = "permission_groupe", joinColumns = {
        @JoinColumn(name = "groupe_id", referencedColumnName = "groupe_id")}, inverseJoinColumns = {
        @JoinColumn(name = "permission_id", referencedColumnName = "permission_id")})
    @ManyToMany
    private List<Permission> permissionList;
    @OneToMany(mappedBy = "groupeId")
    private List<User> userList;

    public Groupe() {
    }

    public Groupe(Integer groupeId) {
        this.groupeId = groupeId;
    }

    public Integer getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Integer groupeId) {
        this.groupeId = groupeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupeId != null ? groupeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupe)) {
            return false;
        }
        Groupe other = (Groupe) object;
        if ((this.groupeId == null && other.groupeId != null) || (this.groupeId != null && !this.groupeId.equals(other.groupeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ashurbanipal.entities.Groupe[ groupeId=" + groupeId + " ]";
    }
    
}
