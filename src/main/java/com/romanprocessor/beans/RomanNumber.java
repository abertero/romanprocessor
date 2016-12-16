package com.romanprocessor.beans;

import com.romanprocessor.enums.RomanCharacter;

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
        return romanRepresentation;
    }

    public String getRomanRepresentation() {
        return romanRepresentation;
    }

    public boolean isValid() {
        return valid;
    }

    private int translateFromRoman() {
        int value = 0;
        for (int i = 0; i < romanCharacters.size(); i++) {

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
                    lastOperationIsSubstract = true;
                    times = 1;
                    lastCharacter = character;
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

    public static void main(String[] args) {
        RomanNumber[] numbers = new RomanNumber[] {
                new RomanNumber("I"),
                new RomanNumber("VX"),
                new RomanNumber("IX"),
                new RomanNumber("XXX"),
                new RomanNumber("XXXX"),
                new RomanNumber("MMXVI")
        };
        System.out.println("Checking valid roman representation");
        for (RomanNumber number : numbers) {
            System.out.println(String.format("%s ==> %s", number, number.checkValidRomanRepresentation()));
        }
    }
}
