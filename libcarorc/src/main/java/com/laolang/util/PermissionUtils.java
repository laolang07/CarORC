package com.laolang.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * 动态申请权限
 * 可以在activity中增加如果权限被拒绝后的弹窗，在onRequestPermissionsResult中判断状态
 *
 * @author lixiao
 * @since 2017-10-25 00:00
 */
public class PermissionUtils {
    public final static int CODE_REQUEST_PERMISSION = 0X123;

    public static boolean checkPermission(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission =
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                //动态申请
                ActivityCompat.requestPermissions(activity, new String[]{

                        Manifest.permission.CAMERA

                }, CODE_REQUEST_PERMISSION);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    public static boolean HavecheckPermission(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission =
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
