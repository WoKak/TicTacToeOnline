package klient;

import java.util.Hashtable;

/**
 * Created by carl on 20.04.2016.
 */
public class Mediator {
    Hashtable<String, Receiver> odbiorcy;

    public Mediator()
    {
        odbiorcy = new Hashtable<String, Receiver>();
    }
    public void dodaj(String tryb, Receiver odbiorca)
    {
        odbiorcy.put(tryb, odbiorca);
    }
    public void powiadom(Message wiadomosc)
    {
        if(odbiorcy.containsKey(wiadomosc.tryb))
            odbiorcy.get(wiadomosc.tryb).powiadom(wiadomosc);
        else
            System.err.println("Brak odbiorcy dla " + wiadomosc.tryb);
    }

}
