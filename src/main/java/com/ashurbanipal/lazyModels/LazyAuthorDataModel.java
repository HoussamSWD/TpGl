/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.lazyModels;

import com.ashurbanipal.entities.Author;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
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
    
    public <T> Long findCountByCriteria(CriteriaQuery<?> criteria) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<?> entityRoot = countCriteria.from(criteria.getResultType());
        countCriteria.select(builder.count(entityRoot));
        countCriteria.where(criteria.getRestriction());

        return em.createQuery(countCriteria).getSingleResult();
    }
    
    public static Long count(final EntityManager em, final CriteriaQuery<?> criteria)
  {
    final CriteriaBuilder builder=em.getCriteriaBuilder();
    final CriteriaQuery<Long> countCriteria=builder.createQuery(Long.class);
    countCriteria.select(builder.count(criteria.getRoots().iterator().next()));
    final Predicate
            groupRestriction=criteria.getGroupRestriction(),
            fromRestriction=criteria.getRestriction();
    if(groupRestriction != null){
      countCriteria.having(groupRestriction);
    }
    if(fromRestriction != null){
      countCriteria.where(fromRestriction);
    }
    countCriteria.groupBy(criteria.getGroupList());
    countCriteria.distinct(criteria.isDistinct());
    return em.createQuery(countCriteria).getSingleResult();
  }

    /**
     * this method get called when loading the data to provide a lazy loading
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
        
       
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);

        Root<Author> form = cq.from(Author.class);

        cq.select(form);

        if (sortField != null) {
            if (sortOrder.name().equals("ASCENDING")) {
                cq.orderBy(cb.desc(form.get(sortField)));
            } else {
                cq.orderBy(cb.asc(form.get(sortField)));
            }
        }

        if (globalFilter != null) {
            Predicate condition = cb.like(form.<String>get("familyName"), "%" + globalFilter.trim() + "%");
            Predicate c2 = cb.or(condition, cb.like(form.<String>get("firstName"), "%" + globalFilter.trim() + "%"));
            cq.where(c2);
        }
        
        this.setRowCount( count(em, cq).intValue());
        
        TypedQuery<Author> q = em.createQuery(cq);

         System.out.println("************************************************************** step2");
        
        
        
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
