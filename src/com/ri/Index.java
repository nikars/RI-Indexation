package com.ri;

import javafx.util.Pair;

import java.util.*;

/**
 * Índice de documentos
 */
public class Index {
    private Map<String, Map<Integer, Integer>> index = new LinkedHashMap<String, Map<Integer, Integer>>();

    public void indexDocument(Document document) {
        for (String token : document.getTokens()) {
            indexToken(document.getId(), token);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Pair<String, Integer>> getAllTokens() {
        List<Pair<String, Integer>> tokenTable = new ArrayList<Pair<String, Integer>>();

        for (Object entry : index.entrySet()) {
            Map.Entry pairs = (Map.Entry) entry;
            tokenTable.add(new Pair<String, Integer>(pairs.getKey().toString(), ((Map<String, Integer>) pairs.getValue()).size()));
        }

        return tokenTable;
    }

    public int getNumberOfDocuments(String token) {
        int numberOfDocuments = 0;
        Map<Integer, Integer> occurrences = index.get(token);

        if(occurrences != null) //Término está en el índice
            numberOfDocuments = occurrences.size();

        return numberOfDocuments;
    }

    public int getTotalOccurrences(String token) {
        int numberOfOccurrences = 0;
        Map<Integer, Integer> occurrences = index.get(token);

        if(occurrences != null) {
            for (Object entry : occurrences.entrySet()) {
                Map.Entry pairs = (Map.Entry) entry;
                numberOfOccurrences += (Integer) pairs.getValue();
            }
        }

        return numberOfOccurrences;
    }

    private void indexToken(int documentId, String token) {
        Map<Integer, Integer> occurrences = index.get(token);

        if(occurrences != null) { // Está en el índice
            Integer currentDocumentFrequency = occurrences.get(documentId);

            if(currentDocumentFrequency != null) // Está en el índice en el mismo documento
                occurrences.put(documentId, ++currentDocumentFrequency);
            else
                occurrences.put(documentId, 1);
        } else { //No está en el índice
            occurrences = new HashMap<Integer, Integer>();

            occurrences.put(documentId, 1);
            index.put(token, occurrences);
        }
    }
}
