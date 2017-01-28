package edu.smartbox.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

/**
 * Created by adityaagrawal on 19/05/16.
 */
public class AppConfig {

    public static String ISLOGIN = "isLogin";
    public static Fragment currentFragment;
    public static String ISLOGINTEACHER = "isLoginadmin";

    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences;
    }


    public static SharedPreferences.Editor getSharedEditor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.edit();
    }
    public static Boolean isLogin(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        boolean isLogin = sharedPreferences.getBoolean(AppConfig.ISLOGIN, false);
        return isLogin;
    }


    public static void login(Context context) {
        SharedPreferences.Editor editor = getSharedEditor(context);
        editor.putBoolean(AppConfig.ISLOGIN, true);

        editor.commit();
    }

    public static Boolean isLoginteacher(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        boolean isLogin = sharedPreferences.getBoolean(AppConfig.ISLOGINTEACHER, false);
        return isLogin;
    }

    public static void loginteacher(Context context) {
        SharedPreferences.Editor editor = getSharedEditor(context);
        editor.putBoolean(AppConfig.ISLOGINTEACHER, true);

        editor.commit();
    }





    public static void logout(Context context) {
        SharedPreferences.Editor editor = getSharedEditor(context);
        editor.putBoolean(AppConfig.ISLOGIN, false);
        editor.commit();
    }
    public static void logoutteacher(Context context) {
        SharedPreferences.Editor editor = getSharedEditor(context);
        editor.putBoolean(AppConfig.ISLOGINTEACHER, false);
        editor.commit();
    }


}
