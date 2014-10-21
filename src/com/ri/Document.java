package com.ri;

import org.apache.tika.language.LanguageIdentifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class Document {
    private String id;
    private List<String> tokens = new ArrayList<String>();
    private String language;

    public Document (String id, String text) {
        this.id = id;

        LanguageIdentifier langIdent = new LanguageIdentifier(text);
        language = langIdent.getLanguage();

        // Eliminamos los signos de puntuación y pasamos las palabras a minúsculas
        tokens = new ArrayList<String>(Arrays.asList(text.replaceAll("[^\\p{L} ]", "").toLowerCase().split("\\s")));
    }

    public List<String> getTokens() {
        return tokens;
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
