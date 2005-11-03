package org.siri.domain;


public class Sender
{
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public String getId() {
        return id;
    }

    String id;
    public Sender(String message, String id) {
        this.message = message;
        this.id = id;
    }



}
