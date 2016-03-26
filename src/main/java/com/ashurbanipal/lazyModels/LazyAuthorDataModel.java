/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.lazyModels;

import com.ashurbanipal.entities.Author;
import com.ashurbanipal.util.JpaUtils;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author swd
 */
public class LazyAuthorDataModel extends LazyDataModel<Author> {
    
    //we don't inject the entity manager, the controller make setem 
    EntityManager em;

    String globalFilter;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public String getGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(String globalFilter) {
        this.globalFilter = globalFilter;
    }

    @Override
    public Object getRowKey(Author a) {
        return a.getAuthorId();
    }


    /**
     * this method get called by primefaces when loading the data to provide a lazy loading
     *
     * @param first is the index of the first row to display
     * @param pageSize how many rows need to be displayed
     * @param sortField the sort filed if not it's null
     * @param sortOrder always ascending or discending
     * @param filters i don't use them hear cuz it's a bit complex
     * @return the list of the authors feched from the db
     */
    @Override
    public List<Author> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Author> authors;
        
        //the standart way to make creteria builder,query, form
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> form = cq.from(Author.class);
        
        //make a select operation of authors (form has authors type)
        cq.select(form);
        
        //if ther is any sorting we add where conditions
        if (sortField != null) {
            if (sortOrder.name().equals("ASCENDING")) {
                cq.orderBy(cb.desc(form.get(sortField)));
            } else {
                cq.orderBy(cb.asc(form.get(sortField)));
            }
        }
        
        //if the user typed in the global filter we add more where conditions
        if (globalFilter != null) {
            Predicate condition = cb.like(form.<String>get("familyName"), "%" + globalFilter.trim() + "%");
            Predicate c2 = cb.or(condition, cb.like(form.<String>get("firstName"), "%" + globalFilter.trim() + "%"));
            cq.where(c2);
        }
        
        //we set the rouw count, primefaces use it to sesplay pages number
        this.setRowCount(JpaUtils.countQueryResults(em, cq).intValue());
        
        //we make the query and we set the result nbr
        TypedQuery<Author> q = em.createQuery(cq);
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        authors = q.getResultList();
        return authors;

    }

    @Override
    public Author getRowData(String rowKey) {
        return em.find(Author.class, Integer.parseInt(rowKey));
    }

}