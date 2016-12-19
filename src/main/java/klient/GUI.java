package klient;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class GUI {

    private final JPanel game;
    private final JPanel login;
    private final JPanel chat;
    private final JFrame frame;
    private final JLabel ipLabel;
    private final JTextField ipTextField;
    private final JButton connectButton;

    private Mediator mediator;

    public GUI() {

        //inicjalizacja paneli
        game = new JPanel();
        chat = new JPanel();
        login = new JPanel();
        game.setBounds(0, 0, 505, 505);
        chat.setBounds(505, 0, 385, 656);
        login.setBounds(890, 0, 250, 500);
        ipLabel = new JLabel("Adres IP serwera");
        ipTextField = new JTextField(15);
        connectButton = new JButton("Połącz");
        connectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connectButtonMouseClicked(evt);
            }
        });
//        game.setBackground(Color.red);
//        chat.setBackground(Color.blue);
//        login.setBackground(Color.yellow);

        //konfiguracja mediatora i logiki gry
        mediator = new Mediator();
        Network network = new Network(mediator);

        TicTacToe ticTacToe = new TicTacToe();
        ChatPanel chatPanel = new ChatPanel();
        LoginPanel loginPanel = new LoginPanel();

        ticTacToe.setMediator(mediator);
        chatPanel.setMediator(mediator);
        loginPanel.setMediator(mediator);

        mediator.dodaj("board_in", ticTacToe);
        mediator.dodaj("chat_in", chatPanel);
        mediator.dodaj("login_in", loginPanel);

        mediator.dodaj("network", network);
        mediator.dodaj("board_out", network);
        mediator.dodaj("chat_out", network);
        mediator.dodaj("login_out", network);

        mediator.dodaj("info", chatPanel);


        //inicjalizacja okna
        frame = new JFrame("Kółko i krzyżyk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(new Dimension(1140, 676));
        frame.setResizable(false);

        //dodawanie głównych paneli
        frame.getContentPane().add(game);
        game.add(ticTacToe);
        frame.getContentPane().add(chat);
        chat.add(chatPanel);
        frame.getContentPane().add(login);
        login.add(loginPanel);
        login.add(ipLabel);
        login.add(ipTextField);
        login.add(connectButton);
        frame.setVisible(true);

        //akcja przy zakmnięciu okna
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                mediator.powiadom(new Message("network", "Bye"));
            }
        });
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    private void connectButtonMouseClicked(java.awt.event.MouseEvent evt){
        mediator.powiadom(new Message("network", ipTextField.getText(), true));
    }
}
