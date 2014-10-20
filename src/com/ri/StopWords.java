package com.ri;

import java.io.*;
import java.util.*;

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

    public String removeSpanishStopWords(String text) {
        return removeWords(text, spanish);
    }

    public String removeEnglishStopWords(String text) {
        return removeWords(text, english);
    }

    private String removeWords(String text, Map map) {
        List<String> words = new ArrayList<String>();
        words.addAll(Arrays.asList(text.split("\\s")));

        for (Iterator<String> iterator = words.iterator(); iterator.hasNext();) {
            String word = iterator.next();
            if (map.containsKey(word)) {
                iterator.remove();
            }
        }

        String clearText = "";

        for (String s : words)
            clearText += s + " ";

        return clearText;
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
