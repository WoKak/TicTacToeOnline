package server;

import klient.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by carl on 21.04.2016.
 */




public class Server {


    ArrayList<Player> players;


    public Server() {
        players = new ArrayList<Player>();
    }

    public static void main(String[] argv) {
        Message wiadomosc = null;
        Boolean isStopped = false;
        Thread playerThread;
        Server server = new Server();
        int newPlayerId = 0;

        MatchMaker mm = new MatchMaker(server.players);

        Thread mmThread = new Thread(mm);

        try {
            ServerSocket socket = new ServerSocket(4444);
            while(!isStopped) {

                Socket clientSocket = socket.accept();
                server.players.add(newPlayerId, new Player(newPlayerId, clientSocket));
                playerThread = new Thread(server.players.get(newPlayerId));
                playerThread.start();
                System.out.println("Gracz id: " + newPlayerId + " się połączył.");
                server.players.get(newPlayerId).send(new Message("info", "Przydzielono id " + newPlayerId));
                newPlayerId++;


                if(!mmThread.isAlive())
                    mmThread.start();



            }
        }
        catch (java.io.IOException e)
        {
            System.out.println("Nie udalo sie otworzyc portu");
        }

    }
}
