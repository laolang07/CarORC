package com.laolang.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class FooSysUtil {
    //单例模式
    private static FooSysUtil mInstance =  new FooSysUtil();
    private FooSysUtil() {}
    public static FooSysUtil getInstance() { return(mInstance); }

    //获取时间戳
    public String getTsp() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"),
                Locale.CHINA);
        return(String.format("%04d-%02d-%02d %02d:%02d:%02d",
                c.get(Calendar.YEAR), (c.get(Calendar.MONTH)+1), c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
    }

    //获取时间戳
    public String getDateStr(String sep) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"),
                Locale.CHINA);
        return(String.format("%04d%s%02d%s%02d", c.get(Calendar.YEAR),sep,
                (c.get(Calendar.MONTH)+1),sep,
                c.get(Calendar.DAY_OF_MONTH)));
    }

    //获取时间戳
    public String getTimeStr(String sep) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"),
                Locale.CHINA);
        return(String.format("%02d%s%02d%s%02d", c.get(Calendar.HOUR_OF_DAY),sep,
                c.get(Calendar.MINUTE),sep,
                c.get(Calendar.SECOND)));
    }

    //将Unix时间戳转换成日期时间字符串
    public String unixTsp2Str(long epoch) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00") );
        Date theDate = new Date(epoch);
        return (sdf.format(theDate) );
    }

    public String toTimeStr(int sec) {
        int hour = sec/3600;
        int min = (sec-hour*3600)/60;
        int sec2 = (sec-hour*3600-min*60);
        return(min+"'"+sec2+"''");
    }

    public String toTimeStr2(int msec) {
        int min = msec/60000;
        int sec = (msec-min*60000)/1000;
        return(min+"'"+sec+"''");
    }

    public String round(double num) {
        final String pattern = "#.##";
        DecimalFormat decimal = new DecimalFormat(pattern);
        return(decimal.format(num) );
    }

    //发送消息到线程消息队列中
    public void postMsgs(Handler handler, String[] keys, String[] values) {
        Bundle bundle = new Bundle();
        for(int i = 0; i < keys.length; ++i) {
            bundle.putString(keys[i], values[i]);
        }
        Message msg = new Message();
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    //发送消息到线程消息队列中
    public void postMsg(Handler handler, String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        Message msg = new Message();
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    //发送消息到线程消息队列中
    public void postMsg(Handler handler, String key, int value) {
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        Message msg = new Message();
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
};
