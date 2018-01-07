package com.example.familyapp.familyapp;

/**
 * Created by sande on 7/01/2018.
 */

public class Leden {
    private String id;
    private String naam;
    private String email;

    public Leden(){

    }

    public Leden(String id, String naam, String email){
        this.id = id;
        this.naam = naam;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getEmail() {
        return email;
    }
}
