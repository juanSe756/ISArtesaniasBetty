package com.artesaniasbetty.controllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityMF {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-betty");
    public static EntityManagerFactory getInstance(){
        return new EntityMF().emf;
    }
}
