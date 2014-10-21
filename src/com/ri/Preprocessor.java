package com.ri;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;
import org.tartarus.snowball.ext.spanishStemmer;

import java.util.List;

/**
 * Preprocesador de tokens
 */
public class Preprocessor {
    public static void removeStopWords(Document document) {
        Cleaner cleaner = new Cleaner();

        if (document.getLanguage().equals("es"))
            cleaner.removeSpanishStopWords(document.getTokens());
        else if (document.getLanguage().equals("en"))
            cleaner.removeEnglishStopWords(document.getTokens());
    }

    public static void stemDocument(Document document) {
        if (document.getLanguage().equals("es"))
            stemDocumentIn(document, new spanishStemmer());
        else if (document.getLanguage().equals("en"))
            stemDocumentIn(document, new englishStemmer());
    }

    public static String stemQuery(String query, String language) {
        if (language.equals("es"))
            return stemQueryIn(query, new spanishStemmer());
        else if (language.equals("en"))
            return stemQueryIn(query, new englishStemmer());
        else return query;
    }

    private static void stemDocumentIn(Document document, SnowballStemmer stemmer) {
        List<String> tokens = document.getTokens();

        for (int i = 0; i < tokens.size(); i++) {
            stemmer.setCurrent(tokens.get(i));
            if (stemmer.stem())
                tokens.set(i, stemmer.getCurrent());
        }
    }

    private static String stemQueryIn(String query, SnowballStemmer stemmer) {
        stemmer.setCurrent(query.toLowerCase());
        return stemmer.stem() ? stemmer.getCurrent() : query;
    }
}
