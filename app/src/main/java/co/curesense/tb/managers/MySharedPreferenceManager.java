package co.curesense.tb.managers;

public class MySharedPreferenceManager {
    private static final MySharedPreferenceManager ourInstance = new MySharedPreferenceManager();

    String MY_PREFS_NAME = "MY_PREFS_NAME";

    public static MySharedPreferenceManager getInstance() {
        return ourInstance;
    }

    private MySharedPreferenceManager() {
    }

    public void saveValue(String name, String value) {

    }
}
