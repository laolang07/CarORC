package com.laolang.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class FooFileUtil {
    //单例模式
    private static FooFileUtil mInstance =  new FooFileUtil();
    private FooFileUtil() {}
    public static FooFileUtil getInstance() { return(mInstance); }

    //按后缀名遍历指定文件夹中的文件（不包括子文件夹）
    public ArrayList<String> list(String path, final String suffix) {
        File dir = new File(path);
        if(!dir.exists()) { return null; }
        //文件名过滤器
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return (filename.endsWith(suffix));
            }
        };

        ArrayList<String> items = new ArrayList<String>();

        //遍历文件
        String[] arr = dir.list(filter);
        for(int i = 0; i < arr.length; ++i) {
            items.add(arr[i]);
        }

        return (items);
    }

    public String getFilename(String url) {
        File f = new File(url);
        return (f.getName());
    }
};
