/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package ua.pp.squirrel.android.utils;

import android.util.Log;

public class LogUtil {
    private final static boolean DISABLE_ALL_LOG = true;

    private final static boolean ERROR_ENABLED = true;
    private final static boolean WARING_ENABLED = false;
    private final static boolean INFO_ENABLED = false;
    private final static boolean DEBUG_ENABLED = true;

    private final static String TAG_SUFFIX = "-SQUIRREL-";

    private static String getTag() {
        return TAG_SUFFIX;
    }

    public static void e(String msg, Throwable tr) {
        if (ERROR_ENABLED && !DISABLE_ALL_LOG) {
            Log.e(getTag(), msg, tr);
        }
    }

    public static void e(String msg) {
        if (ERROR_ENABLED && !DISABLE_ALL_LOG) {
            Log.e(getTag(), msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (WARING_ENABLED && !DISABLE_ALL_LOG) {
            Log.w(getTag(), msg, tr);
        }
    }

    public static void w(String msg) {
        if (ERROR_ENABLED && !DISABLE_ALL_LOG) {
            Log.w(getTag(), msg);
        }
    }

    public static void i(String msg, Throwable tr) {
        if (INFO_ENABLED && !DISABLE_ALL_LOG) {
            Log.i(getTag(), msg, tr);
        }
    }

    public static void i(String msg) {
        if (INFO_ENABLED && !DISABLE_ALL_LOG) {
            Log.i(getTag(), msg);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (DEBUG_ENABLED && !DISABLE_ALL_LOG) {
            Log.d(getTag(), msg, tr);
        }
    }

    public static void d(String msg) {
        if (DEBUG_ENABLED && !DISABLE_ALL_LOG) {
            Log.d(getTag(), msg);
        }
    }
}
