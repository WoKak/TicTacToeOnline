package klient;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicTacToe extends javax.swing.JPanel implements ActionListener, Receiver {

    final JButton[][] buttons;
    Mediator mediator;
    Font myFont = new Font("Arial", Font.PLAIN, 14);

    public TicTacToe(){
        buttons = new JButton[25][25];
        this.setPreferredSize(new Dimension(500, 500));
        GridLayout testgrid = new GridLayout(25, 25);
        this.setLayout(testgrid);
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
                buttons[i][j].setFont(myFont);
                this.add(buttons[i][j]);
                buttons[i][j].addActionListener(this);
            }
        }
    }

    public static void main(String[] argv)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                JPanel game = new TicTacToe();
                JFrame frame = new JFrame("Gra");
                frame.getContentPane().add(game);
                frame.pack();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    //changing board after button has been pressed
    @Override
    public void actionPerformed(ActionEvent event) {
//        Słabe rozwiązanie z 2 pętlami for,
//        ale pracuje nad wykorzystaniem tego .getSource()
//        JButton tmpbutton;
//        tmpbutton = (JButton) event.getSource();

        Message msg;
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                if (event.getSource() == buttons[i][j]) {
                    buttons[i][j].setText("X");
                    buttons[i][j].setEnabled(false);
                    System.out.println("Wcisinieto " + i + ":" + j);

                    //freezingGame();
                    //freezing game until data will be received
                    if (checkingWinner(i, j)) {
                        buttons[i][j].setBackground(Color.red);
                        msg = new Message("board_out", "win", i, j);
                        System.out.println("Game Over");
                        cleanBoard();
                    }
                    else {
                        msg = new Message("board_out", i, j);
                    }
                    //sending message to server    <--------------------
                    mediator.powiadom(msg);
                    freezingGame();
                    //i and j are number of rows and cols
                    //addingOtoBoard(i+1, j+1);
                    //nFreezingGame();
                    //starting game
                }
            }
        }
    }

    //cleaning board
    private void cleanBoard() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                if ("".equals(buttons[i][j].getText())) {
                    buttons[i][j].setVisible(false);
                }
            }
        }
    }

    //restarting game
    private void restartBoard() {
        for(int i = 0; i < 25; i++) {
            for(int j = 0; j < 25; j++) {
                {
                    buttons[i][j].setText("");
                    buttons[i][j].setVisible(true);
                }
            }
        }
        unFreezingGame();
    }

    //adding opponent move, receiving
    private void addingOtoBoard(Message msg) {
        int i = msg.x;
        int j = msg.y;
        buttons[i][j].setText("O");
        buttons[i][j].setEnabled(false);
        System.out.println("Przeciwnik wcisnal " + i + ":" +j);
        if (msg.wiadomosc.equals("lose")) {
            buttons[i][j].setBackground(Color.red);
            System.out.println("Game Over");
            cleanBoard();
        }
    }

    private void freezingGame() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void unFreezingGame() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                if ("".equals(buttons[i][j].getText())) {
                    buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    //looking for winner
    public boolean checkingWinner(int i, int j) {
        int counterH = 4;
        int counterV = 4;
        int counterB1 = 4;
        int counterB2 = 4;
        //checking vertical positions
        if (checkField(i - 1, j)) {
            counterV--;
            if (checkField(i - 2, j)) {
                counterV--;
                if (checkField(i - 3, j)) {
                    counterV--;
                    if (checkField(i - 4, j)) {
                        return true;
                    }
                }
            }
        }
        if (checkField(i + 1, j)) {
            counterV--;
            if (counterV == 0) {
                return true;
            }
            if (checkField(i + 2, j)) {
                counterV--;
                if (counterV == 0) {
                    return true;
                }
                if (checkField(i + 3, j)) {
                    counterV--;
                    if (counterV == 0) {
                        return true;
                    }
                    if (checkField(i + 4, j)) {
                        return true;
                    }
                }
            }
        }
        //checking horizontal positions
        if (checkField(i, j - 1)) {
            counterH--;
            if (checkField(i, j - 2)) {
                counterH--;
                if (checkField(i, j - 3)) {
                    counterH--;
                    if (checkField(i, j - 4)) {
                        return true;
                    }
                }
            }
        }
        if (checkField(i, j + 1)) {
            counterH--;
            if (counterH == 0) {
                return true;
            }
            if (checkField(i, j + 2)) {
                counterH--;
                if (counterH == 0) {
                    return true;
                }
                if (checkField(i, j + 3)) {
                    counterH--;
                    if (counterH == 0) {
                        return true;
                    }
                    if (checkField(i, j + 4)) {
                        return true;
                    }
                }
            }
        }
        //checking bias1 positions
        if (checkField(i - 1, j - 1)) {
            counterB1--;
            if (checkField(i - 2, j - 2)) {
                counterB1--;
                if (checkField(i - 3, j - 3)) {
                    counterB1--;
                    if (checkField(i - 4, j - 4)) {
                        return true;
                    }
                }
            }
        }
        if (checkField(i + 1, j + 1)) {
            counterB1--;
            if (counterB1 == 0) {
                return true;
            }
            if (checkField(i + 2, j + 2)) {
                counterB1--;
                if (counterB1 == 0) {
                    return true;
                }
                if (checkField(i + 3, j + 3)) {
                    counterB1--;
                    if (counterB1 == 0) {
                        return true;
                    }
                    if (checkField(i + 4, j + 4)) {
                        return true;
                    }
                }
            }
        }
        //checking bias2 positions
        if (checkField(i - 1, j + 1)) {
            counterB2--;
            if (checkField(i - 2, j + 2)) {
                counterB2--;
                if (checkField(i - 3, j + 3)) {
                    counterB2--;
                    if (checkField(i - 4, j + 4)) {
                        return true;
                    }
                }
            }
        }
        if (checkField(i + 1, j - 1)) {
            counterB2--;
            if (counterB2 == 0) {
                return true;
            }
            if (checkField(i + 2, j - 2)) {
                counterB2--;
                if (counterB2 == 0) {
                    return true;
                }
                if (checkField(i + 3, j - 3)) {
                    counterB2--;
                    if (counterB2 == 0) {
                        return true;
                    }
                    if (checkField(i + 4, j - 4)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //checking if that field is part of board
    private boolean checkField(int i, int j) {
        try {
            if ("X".equals(buttons[i][j].getText())) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException n) {
            return false;
        }
        return false;
    }

    public void setMediator(Mediator mediator)
    {
        this.mediator = mediator;
    }

    public void powiadom(Message message){
        if(message.wiadomosc.equals("reset")) {
            restartBoard();
        }
        else {
            addingOtoBoard(message);
            unFreezingGame();
        }

    }
}
