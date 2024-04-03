package algonquin.cst2335.finalproject;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String PREF_FILE_NAME = "MyPrefs";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveInput(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getInput(Context context, String key, String defaultValue) {
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    public static void clearInput(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }
}

