package com.ri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton showTokensButton;
    private JLabel infoLabel;
    private JLabel resultLabel;
    private JRadioButton spanishRadioButton;
    private JRadioButton englishRadioButton;
    private JMenuItem about;
    private JMenuItem loadCollectionMenuItem;
    private JMenuItem exitMenuItem;

    private String collectionName = "";
    private Index index;
    private List<File> files = new ArrayList<File>();
    private List<Document> collection = new ArrayList<Document>();
    private boolean loadingSuccess = false;

    public MainWindow() {
        super("Recuperación de Información. Práctica 1: Indexación");
        initGui();

        index = new Index();
    }

    private void loadCollection() {
        PlainTextReader plainTextReader = new PlainTextReader();
        File directory = openDirectory();
        if(directory != null) {
            collectionName = directory.getName();
            addFiles(directory);

            int fileId = 0;
            for (File file : files) {
                collection.add(plainTextReader.readDocument(fileId, file));
                fileId++;
            }
            loadingSuccess = true;
        } else loadingSuccess = false;
    }

    @SuppressWarnings("ConstantConditions")
    private void addFiles(File directory) {
        if(directory != null) {
            for (File fileEntry : directory.listFiles()) {
                if (fileEntry.isDirectory())
                    addFiles(fileEntry);
                else
                    files.add(fileEntry);
            }
        }
        else updateUiAfterLoad(false);
    }

    private File openDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Cargar colección");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();
        else return null;
    }

    private void preprocessCollection() {
        for(Document document : collection) {
            Preprocessor.removeStopWords(document);
            Preprocessor.stemDocument(document);
        }
    }
    
    private void indexCollection() {
        System.out.println(collection.size());
        for (Document document : collection) {
            index.indexDocument(document);
            System.out.println(document.getLanguage()); //TODO delete
        }
    }

    private void search() {
        String token = Preprocessor.stemQuery(searchTextField.getText(), spanishRadioButton.isSelected() ? "es" : "en");

        if(token == null || token.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduzca un término.");
            return;
        }

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

    private void initGui() {
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        initMenu();
        initRadioButtons();
        initListeners();
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initRadioButtons() {
        spanishRadioButton.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(spanishRadioButton);
        group.add(englishRadioButton);
    }

    private void initMenu() {
        JMenuBar menubar  = new JMenuBar();
        JMenu file = new JMenu("Archivo");
        about = new JMenuItem("Acerca de...");

        file.setMnemonic(KeyEvent.VK_F);
        about.setMnemonic(KeyEvent.VK_A);

        loadCollectionMenuItem = new JMenuItem("Cargar colección...");
        exitMenuItem = new JMenuItem("Salir");

        loadCollectionMenuItem.setMnemonic(KeyEvent.VK_O);
        exitMenuItem.setMnemonic(KeyEvent.VK_E);
        loadCollectionMenuItem.setToolTipText("Cargar una colección de documentos desde un directorio");
        exitMenuItem.setToolTipText("Salir del programa");

        file.add(loadCollectionMenuItem);
        file.add(exitMenuItem);
        menubar.add(file);
        menubar.add(about);
        setJMenuBar(menubar);
    }

    @SuppressWarnings("unused")
    private void initListeners() {
        loadCollectionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                loadCollection();
                preprocessCollection();
                indexCollection();
                updateUiAfterLoad(loadingSuccess);
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                MainWindow.this.dispose();
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(MainWindow.this, "Nikolai Arsentiev\nAntonio Romero",
                        "RI: Indexación", JOptionPane.PLAIN_MESSAGE);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        showTokensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TokenTable tokenTable = new TokenTable(index.getAllTokens());
            }
        });
    }
}
