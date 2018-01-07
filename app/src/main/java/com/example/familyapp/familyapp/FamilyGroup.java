package com.example.familyapp.familyapp;



public class FamilyGroup {

    public String id;
    public String name;

    public  FamilyGroup(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
