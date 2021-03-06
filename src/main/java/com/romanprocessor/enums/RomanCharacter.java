package com.romanprocessor.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum RomanCharacter {
    M(1000, true, false, null),
    D(500, false, false, null),
    C(100, true, true, Arrays.asList(new RomanCharacter[]{D, M})),
    L(50, false, false, null),
    X(10, true, true, Arrays.asList(new RomanCharacter[]{L, C})),
    V(5, false, false, null),
    I(1, true, true, Arrays.asList(new RomanCharacter[]{V, X}));

    public static final int ARRAYS_LENGTH = 3;
    public static final RomanCharacter[] SUPERIOR_CHARACTERS = new RomanCharacter[]{M, C, X};
    public static final RomanCharacter[] HALVE_CHARACTERS = new RomanCharacter[]{D, L, V};
    public static final RomanCharacter[] INFERIOR_CHARACTERS = new RomanCharacter[]{C, X, I};

    private int value;
    private boolean canRepeatThreeTimesInARow;
    private boolean canBeSubstracted;
    private List<RomanCharacter> restList;

    RomanCharacter(final int value, final boolean canRepeatThreeTimesInARow, final boolean canBeSubstracted, final List<RomanCharacter> restList) {
        this.value = value;
        this.canRepeatThreeTimesInARow = canRepeatThreeTimesInARow;
        this.canBeSubstracted = canBeSubstracted;
        this.restList = restList;
    }

    public static RomanCharacter getFromName(final String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (RomanCharacter romanCharacter : values()) {
            if (romanCharacter.name().equals(name)) {
                return romanCharacter;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public List<RomanCharacter> getRestList() {
        return restList;
    }

    public boolean isCanRepeatThreeTimesInARow() {
        return canRepeatThreeTimesInARow;
    }

    public boolean isCanBeSubstracted() {
        return canBeSubstracted;
    }
}
