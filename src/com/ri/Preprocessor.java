package com.ri;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;
import org.tartarus.snowball.ext.spanishStemmer;

import java.util.List;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class Preprocessor {
    public static void removeStopWords(Document document) {
        StopWords stopWords = new StopWords();

        if(document.getLanguage() != null) {
            if (document.getLanguage().equals("es"))
                stopWords.removeSpanishStopWords(document.getTokens());
            else
                stopWords.removeEnglishStopWords(document.getTokens());
        }
    }

    public static void stemDocument(Document document) {
        if(document.getLanguage() != null) {
            if (document.getLanguage().equals("es"))
                stemDocuemntIn(document, new spanishStemmer());
            else
                stemDocuemntIn(document, new englishStemmer());
        }
    }

    public static String stemQuery(String query, String language) {
        if (language.equals("es"))
            return stemQueryIn(query, new spanishStemmer());
        else
            return stemQueryIn(query, new englishStemmer());
    }

    private static void stemDocuemntIn(Document document, SnowballStemmer stemmer) {
        List<String> tokens = document.getTokens();

        for (int i = 0; i < tokens.size(); i++) {
            stemmer.setCurrent(tokens.get(i));
            if(stemmer.stem())
                tokens.set(i, stemmer.getCurrent());
        }
    }

    private static String stemQueryIn(String query, SnowballStemmer stemmer) {
        stemmer.setCurrent(query.toLowerCase());
        return stemmer.stem() ? stemmer.getCurrent() : query;
    }
}
