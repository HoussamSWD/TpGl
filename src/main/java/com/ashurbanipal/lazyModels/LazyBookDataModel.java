/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.lazyModels;

import com.ashurbanipal.entities.Author;
import com.ashurbanipal.entities.Book;
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
public class LazyBookDataModel extends LazyDataModel<Book>{
    
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
    public Object getRowKey(Book object) {
       return object.getBookId();
    }

    @Override
    public List<Book> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Book> books = null;
        
        //the standart way to make creteria builder,query, form
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> form = cq.from(Book.class);
        
        //make a select operation of authors (form has authors type)
        cq.select(form);
        
        //if ther is any sorting we add where conditions
        if (sortField != null) {
            if (sortOrder.name().equals("ASCENDING")) {
                cq.orderBy(cb.asc(form.get(sortField)));
            } else {
                cq.orderBy(cb.desc(form.get(sortField)));
            }
        }
        
        //if the user typed in the global filter we add more where conditions
        if (globalFilter != null) {
            Predicate c1 = cb.like(form.<String>get("bookId"), "%" + globalFilter.trim() + "%");
            Predicate c2 = cb.or(c1, cb.like(form.<String>get("title"), "%" + globalFilter.trim() + "%"));
            Predicate c3 = cb.or(c2, cb.like(form.<String>get("subtitle"), "%" + globalFilter.trim() + "%"));
            cq.where(c3);
        }
        
        //we set the rouw count, primefaces use it to sesplay pages number
        this.setRowCount(JpaUtils.countQueryResults(em, cq).intValue());
        
        TypedQuery<Book> query = em.createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        books = query.getResultList();
        
        return books;
    }

    @Override
    public Book getRowData(String rowKey) {
        return em.find(Book.class, rowKey);
    }
    
    
    
}
