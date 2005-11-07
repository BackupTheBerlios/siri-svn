package org.siri.domain;


public class Sender
{
    public void setId(String id) {
        this.id = id;
    }

    String id;
String name;
    public String getName() {
        return name;
    }

    public Sender(String name) {
       this.name = name;

    }

    public void setName(String name) {
        this.name = name;
    }



    public String getId() {
        return id;
    }


    public Sender(String message, String id) {
        this.name = message;
        this.id = id;
    }



}
