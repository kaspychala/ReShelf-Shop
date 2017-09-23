package com.apps.kasper.reshelf;


import android.hardware.Camera;

public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://reshelf.pl/php/log_reg/Login.php";

    // Server user register url
    public static String URL_REGISTER = "http://reshelf.pl/php/log_reg/Register.php";

    // Server refresh books
    public static String URL_REFRESH = "http://reshelf.pl/php/refresh.php";

    public static String photoPathFront = "null";
    public static String photoPathBack = "null";

    public static boolean photoFront = false;
    public static boolean photoBack = false;

    public static RowBook RowBookData[];

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    public static int position=0;

    public static boolean refresh=false;
}