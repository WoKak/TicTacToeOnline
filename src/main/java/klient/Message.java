package klient;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carl on 20.04.2016.
 */
public class Message implements Serializable {
    public String tryb;
    public String wiadomosc;
    public Date date;
    public boolean isHostName;
    public int x, y;

    public Message(String tryb, String wiadomosc)
    {
        this.tryb = tryb;
        this.wiadomosc = wiadomosc;
        this.date = new Date();
        this.isHostName = false;
    }

    public Message(String tryb, int x, int y) {
        this.tryb = tryb;
        this.wiadomosc = "move";
        this.x = x;
        this.y = y;
        this.date = new Date();
        this.isHostName = false;
    }

    public Message(String tryb, String wiadomosc, int x, int y) {
        this.tryb = tryb;
        this.wiadomosc = wiadomosc;
        this.x = x;
        this.y = y;
        this.date = new Date();
        this.isHostName = false;
    }
    public Message(String tryb, String wiadomosc, boolean isHostName) {
        this.tryb = tryb;
        this.wiadomosc = wiadomosc;
        this.date = new Date();
        this.isHostName = isHostName;
    }

    public String toString(){
        return date.toString() + " " + tryb + " " + wiadomosc;
    }
    public String toStringXY() {
        return date.toString() + " " + tryb + " " + x + ", " + y;
    }
}
