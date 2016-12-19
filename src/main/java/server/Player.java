package server;

import klient.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by carl on 16.05.2016.
 */
public class Player implements Runnable {

    ObjectInputStream in;
    ObjectOutputStream out;
    String playerName;
    Player opponent;
    Boolean isStopped;
    int id;

    public Player(int id, Socket socket){
        isStopped = false;
        opponent = null;
        this.id = id;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (java.io.IOException e)
        {
            System.out.println("Problem z połączeniem z graczem nr: " + id);
        }
    }

    public String getPlayerName() {
        return playerName;
    }
    public void setOpponent(Player opponent){
        send(new Message("info", "Rozpoczynasz grę z: " + opponent.getPlayerName()));
        this.opponent = opponent;
    }
    public Boolean isReady()
    {

        return !(opponent != null || playerName == null);
    }

    synchronized public void stop()
    {
        isStopped = true;
    }
    synchronized public void send(Message message){
        try {
            out.writeObject(message);
        } catch (java.io.IOException e){
            System.out.println("Problem z wysłaniem wiadomosc do gracza nr: " + id);
        }
    }

    public void run() {
        Message msg;
        System.out.println("Uruchomiono gracza " + id);
        try {
            while(!isStopped) {
                msg = (Message) in.readObject();

                if (msg.tryb.equals("chat_out"))
                    opponent.send(new Message("chat_in", playerName + ": " + msg.wiadomosc));
                if(msg.tryb.equals("network") && msg.wiadomosc.equals("Bye")) {
                    System.out.println("Gracz id: " + id + " się rozłączył");
                    stop();
                }
                if(msg.tryb.equals("login_out")){
                    this.playerName = msg.wiadomosc;
                    send(new Message("login_in", "Login success"));
                    System.out.println("Gracz id: " + id + " zalogował się z nickiem: " + playerName);
                }

                if(msg.tryb.equals("board_out")){
                    if(msg.wiadomosc.equals("win")){
                        this.send(new Message("info", "Wygrałeś, rozpoczęcie nowej gry w ciągu 5 sekund"));
                        opponent.send(new Message("info", "Przegrałeś, rozpoczęcie nowej gry w ciągu 5 sekund"));
                        opponent.send(new Message("board_in", "lose", msg.x, msg.y));
                        try {
                            Thread.sleep(5000);
                            this.send(new Message("board_in", "reset"));
                            opponent.send(new Message("board_in", "reset"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                        opponent.send(new Message("board_in", msg.x, msg.y));
                }
            }
        }
        catch (java.io.IOException e)
        {
            System.out.println("Nie udalo sie przeczytac obiektu");
        }
        catch (java.lang.ClassNotFoundException e)
        {
            System.out.println("Obiekt innego typu niz oczekiwano");
        }
    }
}
