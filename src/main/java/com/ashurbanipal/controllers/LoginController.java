/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.controllers;

import com.ashurbanipal.entities.Adress;
import com.ashurbanipal.entities.City;
import com.ashurbanipal.entities.Country;
import com.ashurbanipal.entities.User;
import com.ashurbanipal.util.SecurityUtil;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author benboubekeur
 */
@Stateless
public class LoginController {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "com.mycompany_TPGL_war_1.0-SNAPSHOTPU")
    EntityManager em;
    
    public boolean validate(String email, String pwd) throws Exception {
        System.err.println("From validate LoginController");

        //Checking if the user already existe
        User usr = null;
        if ((usr = userExiste(email)) != null) {
            
            System.err.println("We found " + usr.getEmail() + " " + usr.getFamilyName());

            //Getting the user salt
            String salt = usr.getSalt();

            //Hasing the introduce it pwd using the previous salt
            String hashedPwd = SecurityUtil.HashUsingSHA_256(pwd, salt);

            //Compraing the hashed pwd in DB with the new calculated pwd ANd the doing the same thing with email
            if (usr.getPassword().equals(hashedPwd) && usr.getEmail().equals(email)) {
                return true;
            }
            throw new Exception("Wrong PassWord");
        }
        return false;
        
    }
    
    public void addUser(User u) throws NoSuchAlgorithmException, Exception {
        
        Adress adress = u.getAddress();
        String salt = null;
        String hashedPassWord = null;
        if (userExiste(u.getEmail()) == null) {
            try {
                salt = SecurityUtil.getSalt();
                System.err.println("Getting The Salt :" + salt);
                hashedPassWord = SecurityUtil.HashUsingSHA_256(u.getPassword(), salt);
            } catch (NoSuchAlgorithmException e) {
                throw e;
            }
            
            u.setSalt(salt);
            u.setPassword(hashedPassWord);
            em.persist(adress);
            em.persist(u);
            
        } else {
            throw new Exception("This user Already existe ");
        }
        
    }
    
    public User userExiste(String email) {
        List<User> u = null;
        System.err.println("Checking if the user existes.************************");
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);
        u = query.getResultList();
        if (u.isEmpty()) {
            System.err.println("This user doenst existe ************************" + u);
            return null;
        }
        
        System.err.println("WE have found ************************" + u);
        return u.get(0);
    }
    
    public List<Country> getAllCountries() {
        
        TypedQuery<Country> query = em.createNamedQuery("Country.findAll", Country.class);
        return query.getResultList();
    }
    
    public Country findCountry(String countryId) {
        /*TypedQuery<Country> query = em.createNamedQuery("Country.findByCountryId", Country.class);
        query.getSingleResult()*/
        
        return em.find(Country.class, countryId);
    }
    
    public City findCity(String cityId) {
        return em.find(City.class, Integer.valueOf(cityId));
    }
}
