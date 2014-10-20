package com.ri;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class StopWords {
    private Map<String, Object> spanish = new HashMap<String, Object>();
    private Map<String, Object> english = new HashMap<String, Object>();

    public Map<String, Object> getSpanish() {
        return spanish;
    }

    public Map<String, Object> getEnglish() {
        return english;
    }

    public StopWords() {
        File spanishWords = new File("res/palabras_vacias.txt");
        File englishWords = new File("res/stop_words.txt");

        addWords(spanish, spanishWords);
        addWords(english, spanishWords);
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
