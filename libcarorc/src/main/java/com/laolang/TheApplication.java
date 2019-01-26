package com.laolang;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.laolang.util.FooPrefsUtil;

public class TheApplication extends Application {

    //网络监视器
    private FooNetMonitor netMonitor = null;
    private static boolean netAvailable = false;

    @Override
    public void onCreate() {

        //初始化应用程序文件夹
        FooPrefsUtil.getInstance().initAppDir();
        //初始化网络状态监视器
        initNetMonitor();



        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public void onTerminate() {

        //注销网络状态监视器
        this.unregisterReceiver(this.netMonitor);
        //
        System.exit(0);

        // TODO Auto-generated method stub
        super.onTerminate();
    }

    //初始化网络状态监视器
    private void initNetMonitor() {
        //final String sname = Context.CONNECTIVITY_SERVICE;
        //this.conMgr = (ConnectivityManager)this.getSystemService(sname);
        this.netMonitor = new FooNetMonitor();
        final String aname = ConnectivityManager.CONNECTIVITY_ACTION;
        IntentFilter filter = new IntentFilter(aname);
        //注册网络监视器
        this.registerReceiver(netMonitor, filter);
    }

    class FooNetMonitor extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            //获取当前连接是否可用
            final String nextra = ConnectivityManager.EXTRA_NO_CONNECTIVITY;
            boolean unavailable = data.getBoolean(nextra);
            if(unavailable) { //无可用
                netAvailable = false;
            } else { //有可用
                netAvailable = true;
            }
        }
    };

    //网络是否可用
    public static boolean getNetAvailable() {
        return(netAvailable);
    }

    public FooNetMonitor getNetMonitor() {
        return netMonitor;
    }

    public void setNetMonitor(FooNetMonitor netMonitor) {
        this.netMonitor = netMonitor;
    }
};

