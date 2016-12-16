package com.romanprocessor;

import com.romanprocessor.beans.RomanNumber;

public class Main {
    public static void main(String[] args) {
        testRomanCharactersToArabic();
    }

    private static void testRomanCharactersToArabic() {
        RomanNumber[] numbers = new RomanNumber[]{
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
        System.out.println("\n\n");
        System.out.println("Printing arabic representation");
        for (RomanNumber number : numbers) {
            if (!number.isValid()) {
                continue;
            }
            System.out.println(String.format("%s ==> %s", number, number.getArabianRepresentation()));
        }
        System.out.println("\n\n");
    }
}
