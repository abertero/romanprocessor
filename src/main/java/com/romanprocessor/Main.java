package com.romanprocessor;

import com.romanprocessor.beans.RomanNumber;

public class Main {
    public static void main(String[] args) {
        testRomanCharactersToArabic();
        testArabicCharactersToRoman();
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
                new RomanNumber("MDLXVIII"),
                new RomanNumber("MMMCMXCIX")
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

    private static void testArabicCharactersToRoman() {
        RomanNumber[] numbers = new RomanNumber[]{
                new RomanNumber(1),
                new RomanNumber(55),
                new RomanNumber(42),
                new RomanNumber(99),
                new RomanNumber(123),
                new RomanNumber(523),
                new RomanNumber(921),
                new RomanNumber(1023),
                new RomanNumber(1490),
                new RomanNumber(2005),
                new RomanNumber(3999)
        };
        System.out.println("Printing roman representation");
        for (RomanNumber number : numbers) {
            if (!number.isValid()) {
                continue;
            }
            System.out.println(String.format("%s ==> %s", number.getArabianRepresentation(), number.getRomanRepresentation()));
        }
        System.out.println("\n\n");
    }
}
