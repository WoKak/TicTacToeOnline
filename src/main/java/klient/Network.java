package klient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by carl on 21.04.2016.
 */

class NetIn implements Runnable {

    ObjectInputStream in;
    Boolean isStopped;
    Mediator mediator;

    public NetIn(Mediator mediator, ObjectInputStream in)
    {
        isStopped = false;
        this.mediator = mediator;
        this.in = in;
    }

    synchronized public void stop() {
        isStopped = true;
    }

    public void run() {
        Message message;
        while(!isStopped) {
            try {
                message = (Message) in.readObject();
                mediator.powiadom(message);
                System.out.println(message.toString());
            } catch (java.io.IOException e) {
                System.err.println("Nie udalo sie przeczytac obiektu");
                this.stop();
            } catch (ClassNotFoundException e) {
                System.err.println("Obiekt innego typu niz oczekiwano");
            }
        }
    }
}

public class Network implements Receiver {
    public ObjectInputStream in;
    Socket socket;
    ObjectOutputStream out;
    Mediator mediator;
    public Network(Mediator mediator)
    {
        this.mediator = mediator;




    }

    public static void main(String arg[])
    {
        Mediator mediator = new Mediator();
        Network siec = new Network(mediator);
        Message wiadomosc;
        NetIn inN = new NetIn(mediator, siec.in);
        Thread inTh = new Thread(inN);
        inTh.start();
        mediator.dodaj("siec", siec);
        mediator.powiadom(new Message("siec", "Proszę działać"));

        mediator.powiadom(new Message("siec", "Proszę jeszcze raz działać"));
        try {
            Thread.sleep(5000);
        }
        catch (java.lang.InterruptedException e) {
            System.err.println("Nie dają spać");
        }
        inN.stop();
        mediator.powiadom(new Message("siec", "Bye"));
    }

    private void createSocket(String host, int port){
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            NetIn inN = new NetIn(mediator, in);
            Thread inTh = new Thread(inN);
            inTh.start();
        }

        catch(UnknownHostException e){
            System.err.println("Nie udało się znaleźć serwera");
            System.exit(1);
        }
        catch (java.io.IOException e) {
            System.err.println("Nie udało się utworzyć połączenia");
        }
    }

    public void powiadom(Message wiadomosc)
    {
        if(wiadomosc.isHostName)
            createSocket(wiadomosc.wiadomosc, 4444);

        try {
            out.writeObject(wiadomosc);
            System.err.println(wiadomosc.toString());
        }
        catch (java.io.IOException e) {
            System.err.println("Nie udalo sie wyslac wiadomosci");
        }
    }
}
