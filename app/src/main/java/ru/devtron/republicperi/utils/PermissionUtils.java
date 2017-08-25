package ru.devtron.republicperi.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class PermissionUtils {
    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults == null) {
            return true;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasSelfPermissions(Context context, String... permissions) {
        if (permissions == null) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
