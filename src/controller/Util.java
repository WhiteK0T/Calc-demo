package controller;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Util {

    private static String[] rims = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I", " "};
    private static int[] arab = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1, 0};

    public static String ArabToRim(int n) {
        String s = "";
        for (int i = 1; n > 0; i++) {
            while (arab[i] <= n) {
                s = s + rims[i];
                n = n - arab[i];
            }
        }
        return s;
    }

    public static int RimToArab(String roman) {
        if (roman.length() == 0)
            return 0;
        int integerValue = 0;
        int prevNumber = letterToNumber(roman.charAt(0));
        for (int i = 1; i < roman.length(); i++) {
            char ch = roman.charAt(i);
            int number = letterToNumber(ch);
            if (number <= prevNumber)
                integerValue += prevNumber;
            else
                integerValue -= prevNumber;
            prevNumber = number;
        }
        integerValue += prevNumber;
        return integerValue;
    }

    private static int letterToNumber(char letter){
        switch (letter) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new NumberFormatException("Invalid format");
        }
    }

}
