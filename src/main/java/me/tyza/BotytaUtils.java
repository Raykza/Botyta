package me.tyza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class BotytaUtils {
    public final static String dateFormatString = "dd-MM-yy";
    public final static String timeFormatString = "HH:mm:ss";

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }

    /* ---------------------------------------------------------------------------------------- */

    public static String tpsToEmoji(double tps) {
        String emoji = "";

        if (tps <= 7) { emoji = "\uD83D\uDD34"; }
        else if(tps >= 15) { emoji = "\uD83D\uDFE2"; }
        else { emoji = "\uD83D\uDFE1"; }

        return emoji;
    }

    public static String tpsToEmoji(double tps, int min, int max) {
        String emoji = "";

        if (tps <= 7) { emoji = "\uD83D\uDD34"; }
        else if(tps >= 15) { emoji = "\uD83D\uDFE2"; }
        else { emoji = "\uD83D\uDFE1"; }

        return emoji;
    }

    public static String tpsToEmoji(String formattedTPS) {
        if(formattedTPS.contains("*")) formattedTPS = formattedTPS.replace("*","");
        return tpsToEmoji(Double.parseDouble(formattedTPS));
    }

    public static String tpsToEmoji(String formattedTPS, int min, int max) {
        if(formattedTPS.contains("*")) formattedTPS = formattedTPS.replace("*","");
        return tpsToEmoji(Double.parseDouble(formattedTPS), min, max);

    }

    /* ---------------------------------------------------------------------------------------- */

    public static int tpsToColor(double ping) {
        int color = 0x000000;

        /* #79b15a #f5900c #de2e43 */

        if (ping <= 7) { color = 0xDE2E43; }
        else if(ping >= 15) { color = 0x79B15A; }
        else { color = 0xF5900C; }

        return color;
    }

    public static int tpsToColor(double ping, int min, int max) {
        int color = 0x000000;

        /* #79b15a #f5900c #de2e43 */

        if (ping <= min) { color = 0xDE2E43; }
        else if(ping >= max) { color = 0x79B15A; }
        else { color = 0xF5900C; }

        return color;
    }

    public static int tpsToColor(String formattedTPS) {
        if(formattedTPS.contains("*")) formattedTPS = formattedTPS.replace("*","");
        return pingToColor(Long.parseLong(formattedTPS));
    }

    public static int tpsToColor(String formattedTPS, int min, int max) {
        if(formattedTPS.contains("*")) formattedTPS = formattedTPS.replace("*","");
        return pingToColor(Long.parseLong(formattedTPS), min, max);
    }

    /* ---------------------------------------------------------------------------------------- */

    public static String pingToEmoji(long ping) {
        String emoji = "";

        if (ping >= 700) { emoji = "\uD83D\uDD34"; }
        else if(ping <= 400) { emoji = "\uD83D\uDFE2"; }
        else { emoji = "\uD83D\uDFE1"; }

        return emoji;
    }

    public static String pingToEmoji(long ping, int min, int max) {
        String emoji = "";

        if (ping >= max) { emoji = "\uD83D\uDD34"; }
        else if(ping <= min) { emoji = "\uD83D\uDFE2"; }
        else { emoji = "\uD83D\uDFE1"; }

        return emoji;
    }

    public static String pingToEmoji(String formattedPing) {
        if(formattedPing.contains("*")) formattedPing = formattedPing.replace("*","");
        return pingToEmoji(Long.parseLong(formattedPing));
    }

    public static String pingToEmoji(String formattedPing, int min, int max) {
        if(formattedPing.contains("*")) formattedPing = formattedPing.replace("*","");
        return pingToEmoji(Long.parseLong(formattedPing), min, max);

    }

    /* ---------------------------------------------------------------------------------------- */

    public static int pingToColor(long ping) {
        int color = 0x000000;

        /* #79b15a #f5900c #de2e43 */

        if (ping >= 700) { color = 0xDE2E43; }
        else if(ping <= 400) { color = 0x79B15A; }
        else { color = 0xF5900C; }

        return color;
    }

    public static int pingToColor(long ping, int min, int max) {
        int color = 0x000000;

        /* #79b15a #f5900c #de2e43 */

        if (ping >= max) { color = 0xDE2E43; }
        else if(ping <= min) { color = 0x79B15A; }
        else { color = 0xF5900C; }

        return color;
    }

    public static int pingToColor(String formattedPing) {
        if(formattedPing.contains("*")) formattedPing = formattedPing.replace("*","");
        return pingToColor(Long.parseLong(formattedPing));
    }

    public static int pingToColor(String formattedPing, int min, int max) {
        if(formattedPing.contains("*")) formattedPing = formattedPing.replace("*","");
        return pingToColor(Long.parseLong(formattedPing), min, max);
    }

    /* ---------------------------------------------------------------------------------------- */

    public static String formatDate(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(BotytaUtils.dateFormatString).format(localDateTime);
    }

    public static String formatTime(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(BotytaUtils.timeFormatString).format(localDateTime);
    }

    /* ---------------------------------------------------------------------------------------- */

    public static double formatTPS(double tps) {
        return Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }

    public static String tpsToString(double tps) {
        double formattedTPS = formatTPS(tps);
        return formattedTPS + (formattedTPS > 20.0? "*": "");
    }

    public static String[] tpsToString(double[] tps) {
        String[] output = new String[3];
        for(int i = 0; i <= tps.length-1; i++) {
            String formatteedTPS = tpsToString(tps[i]);
            output[i] = tpsToEmoji(formatteedTPS) + "TPS from " + (3*i*i + i + 1) + "mn " +
                        formatteedTPS + "[TPS]";
        }
        return output;
    }

    /* ---------------------------------------------------------------------------------------- */

    public static int meanTPStoColor(double[] tps) {
        double mean = (formatTPS(tps[0]) + formatTPS(tps[1]) + formatTPS(tps[2])) / 3;
        return tpsToColor(mean);
    }

}

