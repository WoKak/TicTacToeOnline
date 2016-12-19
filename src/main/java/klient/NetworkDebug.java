/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

/**
 *
 * @author Michał
 */
public class NetworkDebug implements Receiver {
    
    Mediator mediator;
    
    public void setMediator(Mediator mediator)
    {
        this.mediator = mediator;
    }
    public void powiadom(Message message)
    {
        System.out.println(message.toString());
    }
    
}
