package com.laolang.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class FooUtil {

    //单例模式
    private static FooUtil mInstance =  new FooUtil();
    private FooUtil() {}
    public static FooUtil getInstance() { return(mInstance); }

    /*
     * HashMap导出到Hashtable
     */
    public Hashtable<String, String> Hashtable2map(HashMap<String, String> params) {
        // TODO Auto-generated method stub

        Hashtable<String, String> params2 = new Hashtable<String, String>(params.size() );

        Iterator<String> it = params.keySet().iterator();
        while(it.hasNext() ) {
            String key = it.next();
            String val = params.get(key);
            params2.put(key, val);
        }
        return (params2);
    }
};

