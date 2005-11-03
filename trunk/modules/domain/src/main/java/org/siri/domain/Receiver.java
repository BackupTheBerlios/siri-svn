package org.siri.domain;


public class Receiver {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Receiver(String name, String id) {
        this.name = name;
        this.id = id;
    }


    String name;
    String id;
}
