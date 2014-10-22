package com.ri;

import java.awt.*;

/**
 * Entrada de la aplicación. Inicializa la GUI
 */
public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow();
            }
        });
    }
}
