package fr.nmk.equation_incomplete;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BestTimeRegistered {

    private static final int MODE = Context.MODE_PRIVATE;
    private static final String packageName = ".bestin";

    public static void updateBestTime(Context context, int minute, int seconde, int milliseconde) {

        int bestMinute = getBestMinute(context);
        int bestSeconde = getBestSeconde(context);
        int bestMilliseconde = getBestMilliseconde(context);

        if (bestMinute > minute) {
            saveTime(context, minute, seconde, milliseconde);
        } else if (bestMinute == minute) {
            if (bestSeconde > seconde) {
                saveTime(context, minute, seconde, milliseconde);
            } else if (bestSeconde == seconde) {
                if (bestMilliseconde > milliseconde) {
                    saveTime(context, minute, seconde, milliseconde);
                }
            }
        }

    }

    private static void saveTime(Context context, int minute, int seconde, int milliseconde) {
        final SharedPreferences prefs = context.getSharedPreferences(context.getPackageName() + packageName, MODE);

        final Editor editor = prefs.edit();
        editor.putInt("minute", minute);
        editor.putInt("seconde", seconde);
        editor.putInt("milliseconde", milliseconde);
        editor.commit();
    }

    private static int getBestMinute(final Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(context.getPackageName() + packageName, MODE);

        return prefs.getInt("minute", Integer.MAX_VALUE);
    }

    private static int getBestSeconde(final Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(context.getPackageName() + packageName, MODE);

        return prefs.getInt("seconde", Integer.MAX_VALUE);
    }

    private static int getBestMilliseconde(final Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(context.getPackageName() + packageName, MODE);

        return prefs.getInt("milliseconde", Integer.MAX_VALUE);
    }

    public static String getBestTime(final Context context) {

        int bestMinute = getBestMinute(context);
        int bestSeconde = getBestSeconde(context);
        int bestMilliseconde = getBestMilliseconde(context);

        if (bestMinute == Integer.MAX_VALUE) {
            return "(Not registered yet)";
        }

        return String.format("%02d", bestMinute) + ":" + String.format("%02d", bestSeconde) + ":"
                + String.format("%03d", bestMilliseconde);
    }
}
