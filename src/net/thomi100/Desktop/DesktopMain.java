package net.thomi100.Desktop;

import net.thomi100.VAAPI.SoundUtils;
import net.thomi100.VAAPI.Transformer;
import net.thomi100.VAAPI.VoiceAssistantAPI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/*
 * Created by thomi100 on 18.01.2018.
 */
public class DesktopMain extends JFrame {

    public static void main(String[] args) {

        VoiceAssistantAPI.start();
        openMenu();

    }

    public static void showAlert(String message) {
        try {
            JOptionPane.showMessageDialog(jFrame, message, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String textUpper = "";
    public static String textLower = "";

    public static Transformer transformer;
    public static JFrame jFrame = null;
    public static JLabel outputLabel = null;
    public static void openMenu() {
        int tries = 0;
        while(tries < 20) {
            try {
                transformer = new Transformer(VoiceAssistantAPI.getVoicemap(), VoiceAssistantAPI.getUnknownmap());
                break;
            } catch (IOException e) {
                e.printStackTrace();
                tries++;
                System.err.println("Start aborted! Trying again soon ...");
                showAlert(e.getMessage());
                SoundUtils.sendDoubleBeep();
                try {
                    Thread.sleep(15*1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    showAlert(ex.getMessage());
                    SoundUtils.sendDoubleBeep();
                    break;
                }
            }
        }
        if(transformer == null) {
            System.err.println("Program couldn't be started!");
            System.exit(0);
        }
        JFrame frame = new JFrame();
        frame.setTitle("Titel");
        frame.setSize(375, 100);
        frame.setResizable(false);
        frame.setLocale(null);

        JPanel panel = new JPanel();

        JTextField textField = new JTextField(32);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = e.getActionCommand();
                if(text.length() > 0) {
                    textField.setText("");
                    handleTextinput(text);
                }
            }
        });

        JLabel label = new JLabel("");
        outputLabel = label;
        panel.add(label);

        panel.add(new JLabel("<html><br><br></html>"));

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 38) {
                    if(!textField.getText().equals(textUpper) || textUpper.length() == 0) textLower = textField.getText();
                    textField.setText(textUpper);
                }
                if(e.getKeyCode() == 40) {
                    if(!textField.getText().equals(textLower) || textUpper.length() == 0) textUpper = textField.getText();
                    textField.setText(textLower);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        panel.add(textField);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        jFrame = frame;
    }

    public static void handleTextinput(String text) {
        textUpper = text;
        textLower = "";

        transformer.process(text);
        String output = transformer.transform();

        outputLabel.setText(output);

        transformer.close();
        jFrame.pack();
    }

}
