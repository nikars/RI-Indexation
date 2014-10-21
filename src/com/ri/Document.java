package com.ri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class Document {
    private String id;
    private List<String> tokens = new ArrayList<String>();
    private String language;

    public Document (String id, List<String> tokens) {
        this.id = id;
        this.tokens = tokens;
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
