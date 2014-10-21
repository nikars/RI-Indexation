package com.ri;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton showTokensButton;
    private JLabel infoLabel;
    private JLabel resultLabel;

    private String collectionName = "";
    private Index index;
    private List<File> files = new ArrayList<File>();
    private List<Document> collection = new ArrayList<Document>();

    private static final String sampleDocument = "Como a a a a a a a entrada, tomaremos todos los documentos de un directorio (será nuestra" +
            "colección de documentos) y los procesaremos de forma que seamos capaces de" +
            "identificar los distintos tokens (una vez eliminados signos de puentuación) que" +
            "pasarán al proceso de indexación, contando para cada uno de ellos el número de" +
            "documentos distintos en los que aparecen. Importante, en el proceso eliminaremos" +
            "las palabras vacías (en decsai podeis encontrar dos ficheros con las palabras" +
            "vacías en castellano e inglés).";

    private static final String sampleDocument2 = "El objetivo de la a a a a a a a práctica es conocer los procesos claves en la creación de" +
            "un índice para Recuperación de Información. Se debe trabajar en grupos de dos" +
            "personas. En particular, nos centraremos en las primerasd fases de un proceso" +
            "de indexación que nos permitirán analizar los documentos a indexar para extraer" +
            "finalmente los tokens de indexación" +
            "Al final de la práctica se debe entregar un informe que necesarimente debe" +
            "incluir una sección denominada Trabajo en Grupo en el que se indicará de forma" +
            "clara la contribución de cada alumno.";

    public MainWindow() {
        super("Recuperación de información. Práctica 1: Indexación");
        setContentPane(rootPanel);
        initializeGui();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        index = new Index();
        showTokensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TokenTable tokenTable = new TokenTable(index.getAllTokens());
            }
        });
    }
    
    private void loadCollection() {
        collectionName = "Sample";
    }

    private void preprocessCollection() {
        Document test = new Document("Test Document", new ArrayList<String>(Arrays.asList(sampleDocument.split("\\s"))));
        Document test2 = new Document("Test Document 2", new ArrayList<String>(Arrays.asList(sampleDocument2.split("\\s"))));
        collection.add(test);
        collection.add(test2);
    }
    
    private void indexCollection() {
        for (Document doc : collection)
            index.indexDocument(doc);
    }

    private void search() {
        String word = searchTextField.getText();
        //TODO: Pass Snowball

        int numberOfOcurrences = index.getTotalOccurrences(word);
        if(numberOfOcurrences == 0)
            resultLabel.setText("El término " + "\'" + searchTextField.getText() + "\' no aparece en la colección");
        else
            resultLabel.setText("\'" + searchTextField.getText() + "\' aparece " + numberOfOcurrences +
                    " veces en " + index.getNumberOfDocuments(word) + " documentos");

    }

    private void updateUiAfterLoad(boolean success) {
        infoLabel.setEnabled(true);
        if(success) {
            infoLabel.setText("Colección " + collectionName + " cargada correctamente");
            infoLabel.setForeground(Color.decode("#006699"));

            searchButton.setEnabled(true);
            showTokensButton.setEnabled(true);
        }else {
            infoLabel.setText("Error al cargar la colección");
            infoLabel.setForeground(Color.decode("#990033"));
        }
    }

    private void initializeGui() {
        setLocationRelativeTo(null);
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("Archivo");
        JMenu about = new JMenu("Acerca de...");
        file.setMnemonic(KeyEvent.VK_F);
        about.setMnemonic(KeyEvent.VK_A);

        JMenuItem loadCollectionMenuItem = new JMenuItem("Cargar colección...");
        loadCollectionMenuItem.setMnemonic(KeyEvent.VK_O);
        loadCollectionMenuItem.setToolTipText("Cargar una colección de documentos desde un directorio");
        loadCollectionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                loadCollection();
                preprocessCollection();
                indexCollection();
                updateUiAfterLoad(true);
            }
        });

        file.add(loadCollectionMenuItem);
        menubar.add(file);
        menubar.add(about);
        setJMenuBar(menubar);
    }
    
    private void addFiles() {
        
    }
}
