package com.ri;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // Abrir GUI
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow();
            }
        });
    }
}
