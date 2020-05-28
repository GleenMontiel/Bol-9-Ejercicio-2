package app;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal extends JFrame implements ActionListener, KeyListener {

    ArrayList<JButton> btnList;
    char[] dialPad = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '*', '0', '#' };
    MouseHandler mh = new MouseHandler();
    JTextField txtPulsados;
    JButton btnReset;
    JMenuBar mnuPrincipal;
    JMenu mnuArchivo;
    JMenu mnuMovil;
    JMenuItem mnuGrabar;
    JMenuItem mnuLeer;
    JMenuItem mnuReset;
    JMenuItem mnuSalir;
    JMenuItem mnuAcerca;

    public Principal() {
        super();
        setLayout(null);
        setFocusable(true);

        btnList = new ArrayList<>();

        int x = 10;
        int y = 95;

        for (int i = 0; i < 12; i++) {

            JButton btn = new JButton(String.format("%c", dialPad[i]));
            btn.setSize(200, 30);
            btn.setLocation(x, y);
            btn.addActionListener(this);
            btn.addMouseListener(mh);
            btn.addMouseMotionListener(mh);
            btn.addKeyListener(this);
            this.add(btn);
            if ((i + 1) % 3 == 0) {
                x = 10;
                y += 40;
            } else {
                x += 210;
            }

            btnList.add(btn);
        }

        // Menú Archivo
        mnuGrabar = new JMenuItem("Grabar");
        mnuGrabar.setMnemonic('G');
        mnuGrabar.addActionListener(this);

        mnuLeer = new JMenuItem("Leer");
        mnuLeer.setMnemonic('L');
        mnuLeer.addActionListener(this);

        mnuArchivo = new JMenu("Archivo");
        mnuArchivo.setMnemonic('A');
        mnuArchivo.add(mnuGrabar);
        mnuArchivo.add(mnuLeer);

        // Menú Móvil
        mnuReset = new JMenuItem("Reset");
        mnuReset.setMnemonic('R');
        mnuReset.addActionListener(this);

        mnuSalir = new JMenuItem("Salir");
        mnuSalir.setMnemonic('S');
        mnuSalir.addActionListener(this);

        mnuMovil = new JMenu("Móvil");
        mnuMovil.setMnemonic('M');
        mnuMovil.add(mnuReset);
        mnuMovil.addSeparator();
        mnuMovil.add(mnuSalir);

        // Menú Acerca de...
        mnuAcerca = new JMenuItem("Acerca de...");
        mnuAcerca.setMnemonic('I');
        mnuAcerca.addActionListener(this);

        // Menú Principal
        mnuPrincipal = new JMenuBar();
        mnuPrincipal.add(mnuArchivo);
        mnuPrincipal.add(mnuMovil);
        mnuPrincipal.add(mnuAcerca);
        this.setJMenuBar(mnuPrincipal);

        txtPulsados = new JTextField();
        txtPulsados.setSize(540, 40);
        txtPulsados.setLocation(10, 20);
        txtPulsados.setEditable(false);
        txtPulsados.addKeyListener(this);
        this.add(txtPulsados);

        btnReset = new JButton("Reset");
        btnReset.setSize(70, 40);
        btnReset.setLocation(560, 20);
        btnReset.addActionListener(this);
        btnReset.addMouseListener(mh);
        btnReset.addMouseMotionListener(mh);
        btnReset.addKeyListener(this);
        this.add(btnReset);

        addKeyListener(this);

    }

    public void resetTlf() {

        for (int i = 0; i < btnList.size(); i++) {
            btnList.get(i).setBackground(null);
        }
        txtPulsados.setText("");

    }

    public void mostrarAcerca() {
        JOptionPane.showMessageDialog(null, "Autor: EL MARACUCHO", "Acerca de...", JOptionPane.INFORMATION_MESSAGE);
    }

    public void guardarAgenda() {

        try (PrintWriter f = new PrintWriter(new FileWriter(
                System.getProperty("user.home") + System.getProperty("file.separator") + "agenda.txt", true))) {
            f.println(txtPulsados.getText());
        } catch (IOException e) {
            System.err.println("Error");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mnuGrabar) {

            guardarAgenda();
        }

        if (e.getSource() == mnuLeer) {

            String archivo = System.getProperty("user.home") + System.getProperty("file.separator") + "agenda.txt";
            String texto = "";
            try (Scanner f = new Scanner(new File(archivo))) {
                while (f.hasNext()) {
                    texto += f.nextLine() + "\n";
                }
                JOptionPane.showMessageDialog(null, texto, "Números guardados", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException exc) {
                System.err.println("Error de acceso al archivo: " + exc.getMessage());
            }
        }

        if (e.getSource() == mnuReset) {
            resetTlf();

        }

        if (e.getSource() == mnuSalir) {
            int res = JOptionPane.showConfirmDialog(null, "¿Quieres cerrar el programa?", "Ejercicio 2",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.OK_OPTION)
                System.exit(0);
        }

        if (e.getSource() == mnuAcerca) {
            mostrarAcerca();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isAltDown()) {
            mostrarAcerca();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

        for (int i = 0; i < btnList.size(); i++) {
            if (e.getKeyChar() == btnList.get(i).getText().charAt(0)) {
                txtPulsados.setText(txtPulsados.getText() + btnList.get(i).getText());
            }
        }
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {

            if (!(e.getComponent().getBackground() == Color.GREEN) && e.getComponent() != btnReset) {
                e.getComponent().setBackground(Color.RED);
            }

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {

            if (!(e.getComponent().getBackground() == Color.GREEN && e.getComponent() != btnReset)) {
                e.getComponent().setBackground(null);
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {

            if (e.getComponent() != btnReset) {
                e.getComponent().setBackground(Color.GREEN);
            } else {
                resetTlf();
            }

            for (int i = 0; i < btnList.size(); i++) {
                if (e.getComponent() == btnList.get(i)) {
                    txtPulsados.setText(txtPulsados.getText() + btnList.get(i).getText());
                }
            }

        }
    }
}