package com.ri;

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

    private static final String sampleDocument = "Como a a a a a a a entrada, tomaremos sureña área todos los documentos de un directorio (será nuestra" +
            "colección de documentos) y los procesaremos de forma que seamos capaces de" +
            "identificar los distintos tokens (una vez eliminados signos de puentuación) que" +
            "pasarán al proceso de indexación, contando para cada uno de ellos el número de" +
            "documentos distintos en los que aparecen. Importante, en el proceso eliminaremos" +
            "las palabras vacías (en decsai podeis encontrar dos ficheros con las palabras" +
            "vacías en castellano e inglés).";

    private static final String sampleDocument2 = "Historically, stemmers have often been thought of as either dictionary-based or algorithmic. The presentation of studies of stemming in the literature has perhaps helped to create this division. In the Lovins’ stemmer the algorithmic description is central. In accounts of dictionary-based stemmers the emphasis tends to be on dictionary content and structure, and IR effectiveness. Savoy’s French stemmer (Savoy, 1993) is a good example of this. But the two approaches are not really distinct. An algorithmic stemmer can include long exception lists that are effectively mini-dictionaries, and a dictionary-based stemmer usually needs a process for removing at least i-suffixes to make the look-up in the dictionary possible. In fact in a language in which proper names are inflected (Latin, Finnish, Russian ...), a dictionary-based stemmer will need to remove i-suffixes independently of dictionary look-up, because the proper names will not of course be in the dictionary.";

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
        Document test = new Document("Test Document", sampleDocument);
        Document test2 = new Document("Test Document 2", sampleDocument2);

        Preprocessor.removeStopWords(test);
        Preprocessor.removeStopWords(test2);
        Preprocessor.stemDocument(test);
        Preprocessor.stemDocument(test2);

        collection.add(test);
        collection.add(test2);
    }
    
    private void indexCollection() {
        for (Document doc : collection)
            index.indexDocument(doc);
    }

    private void search() {
        String token = Preprocessor.stemQuery(searchTextField.getText(), "es");

        int numberOfOcurrences = index.getTotalOccurrences(token);
        if(numberOfOcurrences == 0)
            resultLabel.setText("El token " + "\'" + token + "\' no aparece en la colección");
        else
            resultLabel.setText("\'" + token + "\' aparece " + numberOfOcurrences +
                    " veces en " + index.getNumberOfDocuments(token) + " documentos");

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
