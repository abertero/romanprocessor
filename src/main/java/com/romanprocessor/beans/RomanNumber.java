package com.romanprocessor.beans;

import com.romanprocessor.enums.RomanCharacter;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RomanNumber {

    private static final int MAX_REPEAT_TIMES = 3;

    private List<RomanCharacter> romanCharacters;
    private String romanRepresentation;
    private int arabianRepresentation = -1;
    private boolean valid = true;

    public RomanNumber(final String romanRepresentation) {
        this.romanRepresentation = romanRepresentation;
        this.valid = checkValidRomanRepresentation();
        if (valid) {
            this.romanCharacters = new ArrayList<RomanCharacter>();
            for (int i = 0; i < romanRepresentation.length(); i++) {
                romanCharacters.add(RomanCharacter.getFromName(String.valueOf(romanRepresentation.charAt(i))));
            }
        }
    }

    public RomanNumber(final int arabianRepresentation) {
        this.arabianRepresentation = arabianRepresentation;
    }

    public List<RomanCharacter> getRomanCharacters() {
        if (romanCharacters == null) {
            romanCharacters = new ArrayList<RomanCharacter>(translateIntoRoman());
        }
        return new ArrayList<RomanCharacter>(romanCharacters);
    }

    public int getArabianRepresentation() {
        if (arabianRepresentation == -1) {
            arabianRepresentation = translateFromRoman();
        }
        return arabianRepresentation;
    }

    @Override
    public String toString() {
        return getRomanRepresentation();
    }

    public String getRomanRepresentation() {
        if (StringUtils.isBlank(romanRepresentation)) {
            StringBuilder builder = new StringBuilder();
            for (RomanCharacter character : getRomanCharacters()) {
                builder.append(character.name());
            }
            romanRepresentation = builder.toString();
        }
        return romanRepresentation;
    }

    public boolean isValid() {
        return valid;
    }

    private int translateFromRoman() {
        int value = 0;
        for (int i = 0; i < romanCharacters.size(); i++) {
            RomanCharacter currentCharacter = romanCharacters.get(i);
            if (i + 1 < romanCharacters.size()) {
                RomanCharacter nextCharacter = romanCharacters.get(i + 1);
                if (nextCharacter.getValue() > currentCharacter.getValue()) {
                    value += (nextCharacter.getValue() - currentCharacter.getValue());
                } else {
                    value += (nextCharacter.getValue() + currentCharacter.getValue());
                }
                i++;
            } else {
                value += currentCharacter.getValue();
            }
        }
        return value;
    }

    private List<RomanCharacter> translateIntoRoman() {
        return null;
    }

    private boolean checkValidRomanRepresentation() {
        int times = 0;
        boolean lastOperationIsSubstract = false;
        RomanCharacter lastCharacter = null;
        for (int i = 0; i < romanRepresentation.length(); i++) {
            RomanCharacter character = RomanCharacter.getFromName(String.valueOf(romanRepresentation.charAt(i)));
            if (character == null) {
                return false;
            }
            if (lastCharacter == null) {
                times = 1;
                lastCharacter = character;
            } else if (lastCharacter == character) {
                if (!character.isCanRepeatThreeTimesInARow() || times == MAX_REPEAT_TIMES) {
                    return false;
                } else {
                    times++;
                }
            } else if (lastCharacter.getValue() < character.getValue()) {
                if (lastOperationIsSubstract) {
                    return false;
                } else if (lastCharacter.isCanBeSubstracted()) {
                    if (lastCharacter.getRestList().contains(character)) {
                        lastOperationIsSubstract = true;
                        times = 1;
                        lastCharacter = character;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                lastOperationIsSubstract = false;
                times = 1;
                lastCharacter = character;
            }
        }
        return true;
    }
}
