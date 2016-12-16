package com.romanprocessor.beans;

import com.romanprocessor.enums.RomanCharacter;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RomanNumber {

    private static final int MAX_REPEAT_TIMES = 3;
    private static final int MAX_ROMAN_VALUE = 3999;

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
                    value -= currentCharacter.getValue();
                } else {
                    value += currentCharacter.getValue();
                }
            } else {
                value += currentCharacter.getValue();
            }
        }
        return value;
    }

    private List<RomanCharacter> translateIntoRoman() {
        if (arabianRepresentation > MAX_ROMAN_VALUE) {
            return null;
        }
        int convertionHelper = arabianRepresentation;
        List<RomanCharacter> results = new ArrayList<RomanCharacter>();
        while (convertionHelper >= RomanCharacter.M.getValue()) {
            results.add(RomanCharacter.M);
            convertionHelper -= RomanCharacter.M.getValue();
        }

        for (int i = 0; i < RomanCharacter.ARRAYS_LENGTH; i++) {
            RomanCharacter currentSuperior = RomanCharacter.SUPERIOR_CHARACTERS[i];
            RomanCharacter currentHalve = RomanCharacter.HALVE_CHARACTERS[i];
            RomanCharacter currentInferior = RomanCharacter.INFERIOR_CHARACTERS[i];

            if (convertionHelper >= 9 * currentInferior.getValue()) {
                results.add(currentInferior);
                results.add(currentSuperior);
                convertionHelper -= 9 * currentInferior.getValue();
            } else if (convertionHelper >= currentHalve.getValue()) {
                results.add(currentHalve);
                convertionHelper -= currentHalve.getValue();
            } else if (convertionHelper >= 4 * currentInferior.getValue()) {
                results.add(currentInferior);
                results.add(currentHalve);
                convertionHelper -= 4 * currentInferior.getValue();
            } else {
                while (convertionHelper >= currentInferior.getValue()) {
                    results.add(currentInferior);
                    convertionHelper -= currentInferior.getValue();
                }
            }
        }
        return results;
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
