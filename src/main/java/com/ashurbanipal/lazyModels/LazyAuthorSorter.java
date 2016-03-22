/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.lazyModels;

import com.ashurbanipal.entities.Author;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.primefaces.model.SortOrder;

/**
 *
 * @author swd
 */
@Stateless
public class LazyAuthorSorter implements Comparator<Author>{
    
    private String sortField; 
    private SortOrder sortOrder;

    public LazyAuthorSorter() {
    }

    public LazyAuthorSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
    
    

    @Override
    public int compare(Author o1, Author o2) {
        try {
            // get the object usig sort field
            Object val1 = Author.class.getField(this.sortField).get(o1);
            Object val2 = Author.class.getField(this.sortField).get(o2);
            //compare the 2 values
            int sortResult = ((Comparable)val1).compareTo(val2);
            
            //if ascending return the value else return the invers
            return SortOrder.ASCENDING.equals(sortOrder) ? sortResult : -1 * sortResult;
        } catch (Exception ex) {
            System.err.println("Eror in the sort");
        } 
        return 0;
    }
    
}
