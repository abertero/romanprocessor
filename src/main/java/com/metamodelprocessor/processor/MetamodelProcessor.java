package com.metamodelprocessor.processor;

import com.metamodelprocessor.beans.Sentence;
import com.metamodelprocessor.enums.Keywords;
import com.romanprocessor.beans.RomanNumber;
import com.romanprocessor.enums.RomanCharacter;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MetamodelProcessor {
    public static final String OK_RESPONSE = "OK";
    private static final String DEFAULT_RESPONSE = "I have no idea what you are talking about";
    private Map<String, RomanCharacter> wordToRoman = new HashMap<String, RomanCharacter>();
    private Map<String, Double> resourceToCredits = new HashMap<String, Double>();
    private static MetamodelProcessor instance = null;

    private MetamodelProcessor() {
    }

    public static MetamodelProcessor getInstance() {
        if (instance == null) {
            instance = new MetamodelProcessor();
        }
        return instance;
    }

    public RomanCharacter getRomanCharacter(final String word) {
        return wordToRoman.get(word);
    }

    public Double getCredits(final String resource) {
        return resourceToCredits.get(resource);
    }

    public String processSentence(final Sentence sentence) {
        if (sentence.getType() == null) {
            return DEFAULT_RESPONSE;
        }
        switch (sentence.getType()) {
            case DEFINITION:
                return processDefinitionSentence(sentence);
            case QUERY:
                return processQuerySentence(sentence);
            default:
                return DEFAULT_RESPONSE;
        }
    }

    private String processDefinitionSentence(final Sentence sentence) {
        String stringSentence = sentence.getSentenceAsString();
        String[] splittedSentence = stringSentence.split(Keywords.SPLIT_TOKEN.getWord());
        if (stringSentence.contains(Keywords.CREDITS.getWord())) {
            int indexOfIs = indexOfWord(splittedSentence, Keywords.IS.getWord());
            if (indexOfIs == -1 || splittedSentence.length - 3 != indexOfIs) {
                return DEFAULT_RESPONSE;
            }
            int totalCredits = Integer.parseInt(splittedSentence[indexOfIs + 1]);
            String element = splittedSentence[indexOfIs - 1];
            int indexOfElement = indexOfWord(splittedSentence, element);
            if (indexOfElement == 0) {
                resourceToCredits.put(element, (double) totalCredits);
            } else {
                StringBuilder romanNumberBuilder = new StringBuilder();
                for (int i = 0; i < indexOfElement; ++i) {
                    String wordToTranslate = splittedSentence[i];
                    RomanCharacter romanCharacter = wordToRoman.get(wordToTranslate);
                    romanNumberBuilder.append(romanCharacter.name());
                }
                RomanNumber romanNumber = new RomanNumber(romanNumberBuilder.toString());
                if (!romanNumber.isValid()) {
                    return DEFAULT_RESPONSE;
                }
                int quantity = romanNumber.getArabianRepresentation();
                resourceToCredits.put(element, totalCredits / ((double) quantity));
            }
        } else {
            if (splittedSentence.length != 3) {
                return DEFAULT_RESPONSE;
            }
            RomanCharacter romanCharacter = RomanCharacter.getFromName(splittedSentence[2]);
            if (romanCharacter == null) {
                return DEFAULT_RESPONSE;
            }
            wordToRoman.put(splittedSentence[0], romanCharacter);
        }
        return OK_RESPONSE;
    }

    private String processQuerySentence(final Sentence sentence) {
        String stringSentence = sentence.getSentenceAsString();
        String[] splittedSentence = stringSentence.split(Keywords.SPLIT_TOKEN.getWord());
        if (stringSentence.contains(Keywords.HOW_MUCH.getWord())) {
            if (!Keywords.QUESTION_MARK.getWord().equals(splittedSentence[splittedSentence.length - 1])) {
                return DEFAULT_RESPONSE;
            }
            int indexOfIs = indexOfWord(splittedSentence, Keywords.IS.getWord());
            StringBuilder romanBuilder = new StringBuilder();
            StringBuilder originalElement = new StringBuilder();
            for (int i = indexOfIs + 1; i < splittedSentence.length - 1; ++i) {
                originalElement.append(splittedSentence[i]).append(" ");
                romanBuilder.append(wordToRoman.get(splittedSentence[i]).name());
            }
            RomanNumber romanNumber = new RomanNumber(romanBuilder.toString());
            if (!romanNumber.isValid()) {
                return DEFAULT_RESPONSE;
            }
            return String.format("%s is %s", originalElement.toString().trim(), romanNumber.getArabianRepresentation());
        } else {
            if (!Keywords.QUESTION_MARK.getWord().equals(splittedSentence[splittedSentence.length - 1])) {
                return DEFAULT_RESPONSE;
            }
            String resource = splittedSentence[splittedSentence.length - 2];
            if (!resourceToCredits.containsKey(resource)) {
                return DEFAULT_RESPONSE;
            }
            int indexOfIs = indexOfWord(splittedSentence, Keywords.IS.getWord());
            StringBuilder romanBuilder = new StringBuilder();
            StringBuilder originalElement = new StringBuilder();
            for (int i = indexOfIs + 1; i < splittedSentence.length - 2; ++i) {
                originalElement.append(splittedSentence[i]).append(" ");
                romanBuilder.append(wordToRoman.get(splittedSentence[i]).name());
            }
            RomanNumber romanNumber = new RomanNumber(romanBuilder.toString());
            if (!romanNumber.isValid()) {
                return DEFAULT_RESPONSE;
            }
            double resourceInQuantity = romanNumber.getArabianRepresentation() * resourceToCredits.get(resource);
            return String.format("%s %s is %.0f Credits", originalElement.toString().trim(), resource, resourceInQuantity);
        }
    }

    private int indexOfWord(final String[] split, final String word) {
        if (StringUtils.isBlank(word)) {
            return -1;
        }
        int i = 0;
        for (String element : split) {
            if (word.equals(element)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "MetamodelProcessor{" +
                "wordToRoman=" + wordToRoman +
                ", resourceToCredits=" + resourceToCredits +
                '}';
    }
}
