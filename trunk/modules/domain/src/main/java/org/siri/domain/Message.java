package org.siri.domain;

import java.util.Set;
import java.util.HashSet;


public class Message
{
    public String getId() {
        return id;
    }

    String id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set getReceivers() {
        return receivers;
    }

    public void setReceivers(Set receivers) {
        this.receivers = receivers;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    String message;
    Set receivers = new HashSet();
    Sender sender;

    
}
