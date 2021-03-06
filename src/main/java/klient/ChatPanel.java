/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.awt.EventQueue;
import static java.awt.event.KeyEvent.KEY_PRESSED;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import javafx.scene.input.KeyCode;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Michał
 */
public class ChatPanel extends javax.swing.JPanel implements Receiver{


    Mediator mediator;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatLogTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea mesTextField;
    private javax.swing.JButton sendButton;

    /**
     * Creates new form ChatPanel
     */
    public ChatPanel() {
        initComponents();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Mediator mediator = new Mediator();
                ChatPanel chat = new ChatPanel();
                JFrame frame = new JFrame("Czat");
                NetworkDebug network = new NetworkDebug();
                frame.getContentPane().add(chat);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                chat.setMediator(mediator);
                mediator.dodaj("chat", chat);
                network.setMediator(mediator);
                mediator.dodaj("network", network);


                String s;
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                //while (!s.equals("/quit"))
                {
                    try {
                        s = in.readLine();
                        mediator.powiadom(new Message("chat", "Przeciwnik: " + s));
                    }
                    catch (IOException e)
                    {
                        System.err.println("Błąd I/O");
                    }
                }

            }
        });
    }

    public void setMediator(Mediator mediator)
    {
        this.mediator = mediator;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mesTextField = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatLogTextField = new javax.swing.JTextArea();

        mesTextField.setColumns(20);
        mesTextField.setRows(5);
        mesTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mesTextFieldKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(mesTextField);

        sendButton.setText("Wyślij");
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonMouseClicked(evt);
            }
        });
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        sendButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sendButtonKeyPressed(evt);
            }
        });

        chatLogTextField.setEditable(false);
        chatLogTextField.setColumns(20);
        chatLogTextField.setRows(5);
        jScrollPane2.setViewportView(chatLogTextField);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 145, Short.MAX_VALUE)
                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendButtonActionPerformed

    private void sendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonMouseClicked
        // TODO add your handling code here:

        if (!mesTextField.getText().equals("")) {

            chatLogTextField.append(new Date().toString() + " Ja: " +  mesTextField.getText() + "\n");
            mediator.powiadom(new Message("chat_out", mesTextField.getText()));
            mesTextField.setText("");

        }
    }//GEN-LAST:event_sendButtonMouseClicked

    private void sendButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sendButtonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendButtonKeyPressed
    // End of variables declaration//GEN-END:variables

    private void mesTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mesTextFieldKeyPressed
        // TODO add your handling code here:

        /*if ( evt.getCode()== KeyCode.ENTER) {
            chatLogTextField.append(new Date().toString() + " " + mesTextField.getText());
            mesTextField.setText("");
        }*/
    }//GEN-LAST:event_mesTextFieldKeyPressed

    public void powiadom(Message wiadomosc)
    {
        chatLogTextField.append(new Date().toString() + " " +  wiadomosc.wiadomosc + "\n");
    }
}
