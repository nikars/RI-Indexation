package com.ri;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by Nikolai on 20/10/2014.
 */
public class Index {
    private Map<String, Map<String, Integer>> index = new HashMap<String, Map<String, Integer>>();

    public void indexDocument(Document doc) {
        for (String token : doc.tokens) {
            indexToken(doc.id, token);
        }
    }

    public List<Pair<String, Integer>> getAllTokens() {
        List<Pair<String, Integer>> tokenTable = new ArrayList<Pair<String, Integer>>();

        Iterator iterator = index.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry)iterator.next();
            tokenTable.add(new Pair<String, Integer>(pairs.getKey().toString(), ((Map<String, Integer>)pairs.getValue()).size()));
//            iterator.remove(); // avoids a ConcurrentModificationException
        }

        return tokenTable;
    }

    public int getNumberOfDocuments(String token) {
        int numberOfDocuments = 0;
        Map<String, Integer> occurrences = index.get(token);

        if(occurrences != null) //Término está en el índice
            numberOfDocuments = occurrences.size();

        return numberOfDocuments;
    }

    public int getTotalOccurrencess(String token) {
        int numberOfOccurrences = 0;
        Map<String, Integer> occurrences = index.get(token);



        Iterator iterator = index.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            numberOfOccurrences += (Integer) pairs.getValue();
//            iterator.remove(); // avoids a ConcurrentModificationException
        }

        return numberOfOccurrences;
    }

    private void indexToken(String document, String token) {
        Map<String, Integer> occurrences = index.get(token);

        if(occurrences != null) { // Está en el índice
            Integer currentDocumentFrequency = occurrences.get(document);

            if(currentDocumentFrequency != null) // Está en el índice en el mismo documento
                currentDocumentFrequency ++;
            else
                occurrences.put(document, 1);
        } else { //No está en el índice
            occurrences = new HashMap<String, Integer>();

            occurrences.put(document, 1);
            index.put(token, occurrences);
        }
    }
}
