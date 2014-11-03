package com.ri;

import org.apache.tika.language.LanguageIdentifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Documento de la colección dividido en tokens
 */
public class Document {
    private int id;
    private List<String> tokens = new ArrayList<String>();
    private String language;

    public Document (int id, String text) {
        this.id = id;

        LanguageIdentifier identifier = new LanguageIdentifier(text);
        language = identifier.getLanguage();

        // Eliminamos los signos de puntuación y pasamos las palabras a minúsculas
        tokens = new ArrayList<String>(Arrays.asList(text.replaceAll("[^\\p{L}\\p{N}]", " ").toLowerCase().split("\\s")));
    }

    public List<String> getTokens() {
        return tokens;
    }

    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }
}
