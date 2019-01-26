package com.laolang.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;

import com.laolang.IDef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FooPicUtil {

    //单例模式
    private static FooPicUtil instance =  new FooPicUtil();
    private FooPicUtil() {}
    public static FooPicUtil getInstance() { return(instance); }

    //获取本地位图(缩略图)
    public Bitmap getLocalBitmap(String filePath, int width, int height) {
        if(filePath==null) {
            return (null);
        }
        Bitmap source = BitmapFactory.decodeFile(filePath);
        Bitmap thumb = ThumbnailUtils.extractThumbnail(source,width,height);
        if(source!=null&&source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return(thumb);
    }

    //获取本地位图(缩略图)
    public Bitmap getLocalBitmap(Bitmap source, int width, int height) {
        if(source==null) {
            return (null);
        }
        Bitmap thumb = ThumbnailUtils.extractThumbnail(source,width,height);
        if(source!=null&&source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return(thumb);
    }

    //保存图片
    public String saveBitmap(Bitmap raw, String filePath) {
        File f = new File(filePath);

        Log.d(IDef.App_Tag, "raw width="+raw.getWidth()+",raw height="+raw.getHeight());

        try {
            FileOutputStream fos = new FileOutputStream(f);
            raw.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return (filePath);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return (null);
    }
};