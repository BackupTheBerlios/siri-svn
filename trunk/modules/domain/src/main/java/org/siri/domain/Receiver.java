package org.siri.domain;


public class Receiver
{
    private String id;
    private String name;

    public Receiver()
    {
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }

    Message message;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public Receiver(String name, String id)
    {
        this.name = name;
        this.id = id;
    }


    private void setId(String id)
    {
        this.id = id;
    }


}
