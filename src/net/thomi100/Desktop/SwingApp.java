package net.thomi100.Desktop;

import net.thomi100.VAAPI.MyChanges;
import net.thomi100.VAAPI.SoundUtils;
import net.thomi100.VAAPI.Transformer;
import net.thomi100.VAAPI.VoiceAssistantAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

/*
 * Created by thomi100 on 09.02.2018.
 */
public class SwingApp extends JFrame {

    private static SwingApp app;
    private static JTextArea outputArea;
    private static Transformer transformer;

    public static void main(String[] args) {

        SwingApp beispiel = new SwingApp("Lia - idle");
        beispiel.setVisible(true);
        app = beispiel;

        VoiceAssistantAPI.start();

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


    }

    public static void showAlert(String message) {
        try {
            JOptionPane.showMessageDialog(app, message, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SwingApp(String title) {
        super(title);

            /* layout */
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Component c = this;

            /* buttons, text fields */
        JButton quit = new JButton("quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(quit);

        JButton startListening = new JButton("start listening");
        startListening.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        add(startListening);

        JButton stopListening = new JButton("stop listening");
        stopListening.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        add(stopListening);

        JButton isDebug = new JButton("debug enabled");
        add(isDebug);
        isDebug.setVisible(false);

        JButton debug = new JButton("switch debug");
        debug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyChanges.DEBUG = !MyChanges.DEBUG;
                if(MyChanges.DEBUG) {
                    isDebug.setVisible(true);
                } else {
                    isDebug.setVisible(false);
                }
            }
        });
        add(debug);

        JTextField textfield = new JTextField("input");
        textfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(textfield.getText());
                textfield.setText("");
                process(textfield.getText());
            }
        });
        add(textfield);

        JTextArea textarea = new JTextArea("output");
        textarea.setFocusable(false);
        add(textarea);
        outputArea = textarea;

            /* Menu bar */
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("This is a test");
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Teeeee", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItem);

        menu.addSeparator();

        setJMenuBar(menuBar);

            /* things */
        pack();
        setSize(480, 270);
        setResizable(true);
    }

    public static void process(String input) {
        app.setTitle("Lia - processing...");

        transformer.process(input);
        String output = transformer.transform();

        outputArea.setText(output);
        app.pack();

        transformer.close();
        app.setTitle("Lia - idle");
    }

}
