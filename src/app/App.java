package app;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        Principal frame = new Principal();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(655, 350);
        frame.setVisible(true);
    }
}
