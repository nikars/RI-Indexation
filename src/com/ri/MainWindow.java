package com.ri;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JTextField textField1;
    private JButton buscarTérminoButton;
    private JButton listaDeTokensButton;

    public MainWindow() {
        super("Recuperación de información. Práctica 1: Indexación");
        setContentPane(rootPanel);
        initializeGui();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);



    }

    private void initializeGui() {
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("Archivo");
        JMenu about = new JMenu("Acerca de...");
        file.setMnemonic(KeyEvent.VK_F);
        about.setMnemonic(KeyEvent.VK_A);

        JMenuItem eMenuItem = new JMenuItem("Cargar colección...");
        eMenuItem.setMnemonic(KeyEvent.VK_O);
        eMenuItem.setToolTipText("Cargar una colección de documentos desde un directorio");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        file.add(eMenuItem);
        menubar.add(file);
        menubar.add(about);
        setJMenuBar(menubar);
    }
}
