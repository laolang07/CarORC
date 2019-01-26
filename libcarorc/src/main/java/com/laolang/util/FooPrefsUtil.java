package com.laolang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.laolang.IDef;

import java.io.File;

public class FooPrefsUtil {
    //单例模式
    private static FooPrefsUtil mInstance =  new FooPrefsUtil();
    private FooPrefsUtil() {}
    public static FooPrefsUtil getInstance() { return(mInstance); }

    public String getString(Context ctx, String  key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return(prefs.getString(key, ""));
    }

    public void setString(Context ctx, String  key, String val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public int getInt(Context ctx, String  key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return(prefs.getInt(key, 0));
    }

    public void setInt(Context ctx, String  key, int val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = prefs.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    //初始化应用文件夹
    public void initAppDir() {
        File f = new File(IDef.App_Dir);
        if(!f.exists()) {
            f.mkdir();
        }
    }

    public String getAppDir() {
        final String appDir = Environment.getExternalStorageDirectory()+File.separator+IDef.App_Tag;
        return(appDir);
    }

    public String getUploadDir() {
        final String appDir = Environment.getExternalStorageDirectory()+File.separator+IDef.App_Tag;
        final String upload = appDir+File.separator+"upload";
        return(upload);
    }
};