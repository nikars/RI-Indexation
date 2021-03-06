package com.ri;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Eliminador de palabras vacías
 */
public class Cleaner {
    private Map<String, Object> spanish = new HashMap<String, Object>();
    private Map<String, Object> english = new HashMap<String, Object>();

    public Cleaner() {
        File spanishWords = new File("res/palabras_vacias.txt");
        File englishWords = new File("res/stop_words.txt");

        addWords(spanish, spanishWords);
        addWords(english, englishWords);
    }

    public void removeSpanishStopWords(List<String> tokens) {
        removeWords(tokens, spanish);
    }

    public void removeEnglishStopWords(List<String> tokens) {
        removeWords(tokens, english);
    }

    private void removeWords(List<String> tokens, Map map) {
        for (Iterator<String> iterator = tokens.iterator(); iterator.hasNext();) {
            String token = iterator.next();
            if (map.containsKey(token))
                iterator.remove();
        }
    }

    @SuppressWarnings("unchecked")
    private void addWords(Map map, File file) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert fileReader != null;
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null)
                map.put(line, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
