
package com.example.and319;

import android.util.Log;


public class LogUtil {
    private static final String TAG = "labels_tag";
    private static boolean debug=true;

    private LogUtil() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        LogUtil.debug = debug;
    }

    public static void d(String message) {
        if (debug) {
            Log.d(TAG, message);
        }
    }

    public static void w(String message) {
        if (debug) {
            Log.w(TAG, message);
        }
    }

    public static void e(String message) {
        if (debug) {
            Log.e(TAG, message);
        }
    }

    public static void i(String message) {
        if (debug) {
            Log.i(TAG, message);
        }
    }
}
