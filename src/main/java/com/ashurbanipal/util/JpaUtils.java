/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.util;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author swd
 */
public class JpaUtils {
    
    
    public static Long countQueryResults(final EntityManager em, final CriteriaQuery<?> criteria) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        countCriteria.select(builder.count(criteria.getRoots().iterator().next()));
        final Predicate groupRestriction = criteria.getGroupRestriction(),
                fromRestriction = criteria.getRestriction();
        if (groupRestriction != null) {
            countCriteria.having(groupRestriction);
        }
        if (fromRestriction != null) {
            countCriteria.where(fromRestriction);
        }
        countCriteria.groupBy(criteria.getGroupList());
        countCriteria.distinct(criteria.isDistinct());
        return em.createQuery(countCriteria).getSingleResult();
    }
}
