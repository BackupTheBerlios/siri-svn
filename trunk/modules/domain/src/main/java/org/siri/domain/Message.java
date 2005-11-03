package org.siri.domain;

import java.util.Set;
import java.util.HashSet;


public class Message
{
    String id;
    String message;
    Set receivers = new HashSet();
    Sender sender;
    
    
}
