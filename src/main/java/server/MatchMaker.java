package server;

import java.util.ArrayList;

/**
 * Created by carl on 17.05.2016.
 */
public class MatchMaker implements Runnable {
    ArrayList<Player> players;
    Boolean isStopped;
    public MatchMaker(ArrayList<Player> players)
    {
        isStopped = false;
        this.players = players;
    }
    public void stop(){
        isStopped = true;
    }
    public void run()
    {
        Player player1, player2;
        System.out.println("Rozpoczęto parowanie graczy");
        while(!isStopped)
        {
            for(int i =0; i < players.size(); i++)
                if(players.get(i).isReady())
                {
                    player1 = players.get(i);
                    for(int j = 0; j < players.size(); j++)
                    {
                        if(j != i)
                            if(players.get(j).isReady()) {
                                player2 = players.get(j);

                                player1.setOpponent(player2);
                                player2.setOpponent(player1);
                                System.out.println("Utworzono nową rozgrywkę");
                            }
                    }
                }
        }
    }
}
