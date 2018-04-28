package com.example.apoorva.gpandroid;

/**
 * Created by Apoorva on 4/28/2018.
 */

public class User {
    //Variables to maintain information about the user
    private String id;
    private String name;
    private String email;
    public User(){
    }
    public User( String id,String name,String email)
    {
        this.id = id;
        this.email = email;
        this.name = name;
    }
    public void setName(String a)
    {
        this.name  = a;
    }
    public void setEmail(String a)
    {
        this.email = a;
    }
    public String getName()
    {
        return name;
    }
    public String getId()
    {
        return id;
    }
}
