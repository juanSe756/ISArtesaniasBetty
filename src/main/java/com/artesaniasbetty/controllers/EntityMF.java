package com.artesaniasbetty.controllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityMF {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-betty");
    public static EntityManagerFactory getInstance(){
        return emf;
    }
}
