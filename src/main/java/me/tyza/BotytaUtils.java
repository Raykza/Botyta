package me.tyza;

import java.util.Arrays;

public class BotytaUtils {
    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }
}

