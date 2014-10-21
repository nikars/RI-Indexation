package com.ri;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class StopWords {
    private Map<String, Object> spanish = new HashMap<String, Object>();
    private Map<String, Object> english = new HashMap<String, Object>();

    public StopWords() {
        File spanishWords = new File("res/palabras_vacias.txt");
        File englishWords = new File("res/stop_words.txt");

        addWords(spanish, spanishWords);
        addWords(english, englishWords);
    }

    public void removeSpanishStopWords(List<String> tokenizedText) {
        removeWords(tokenizedText, spanish);
    }

    public void removeEnglishStopWords(List<String> tokenizedText) {
        removeWords(tokenizedText, english);
    }

    private void removeWords(List<String> tokenizedText, Map map) {
        for (Iterator<String> iterator = tokenizedText.iterator(); iterator.hasNext();) {
            String token = iterator.next();
            if (map.containsKey(token))
                iterator.remove();
        }
    }

    private void addWords(Map map, File file) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                map.put(line, null);
            }
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
