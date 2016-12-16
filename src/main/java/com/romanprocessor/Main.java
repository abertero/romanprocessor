package com.romanprocessor;

import com.romanprocessor.beans.RomanNumber;

public class Main {
    public static void main(String[] args) {
        printValidBooleanRepresentation();
    }

    private static void printValidBooleanRepresentation() {
        RomanNumber[] numbers = new RomanNumber[] {
                new RomanNumber("I"),
                new RomanNumber("VX"),
                new RomanNumber("IX"),
                new RomanNumber("XXX"),
                new RomanNumber("XXXX"),
                new RomanNumber("MMXVI"),
                new RomanNumber("MDLXIX"),
                new RomanNumber("MDLXVIII")
        };
        System.out.println("Checking valid roman representation");
        for (RomanNumber number : numbers) {
            System.out.println(String.format("%s ==> %s", number, number.isValid()));
        }
    }
}
