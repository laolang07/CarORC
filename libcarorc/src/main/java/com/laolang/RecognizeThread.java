package com.laolang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.laolang.libcarorc.CarORCCaptureActivity;
import com.wintone.plateid.ConfigArgument;
import com.wintone.plateid.PlateIDAPI;
import com.wintone.plateid.TH_PlateIDResult;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 识别线程
 * @author PAUL
 * @since 2015.6
 */
public class RecognizeThread extends Thread {

    private Handler handler = null;
    private String filePath = null;

    public RecognizeThread(Handler handler, String filePath) {
        this.handler = handler;
        this.filePath = filePath;
    }

    @Override
    public void run() {

        final String result = recognizeImageFile(this.filePath);
        //返回消息
        Message msg = new Message();
        msg.what = IDef.Msg_Recognized_Finished;

        if(result != null) {
            Bundle data = new Bundle();
            data.putString(IDef.Extra_Recog_Result, result);
            msg.setData(data);
        }
        this.handler.sendMessage(msg);
    }

    /**
     * 识别车牌图片文件
     * @param filePath2 文件路径
     * @return 车牌颜色+车牌号码
     */
    private String recognizeImageFile(String filePath2) {

        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        TH_PlateIDResult localResult = new TH_PlateIDResult();
        int[] array1 = new int[1];
        array1[0] = 10;
        int[] array2 = new int[1];
        array2[0] = -1;

        File file = new File(filePath);
        if(!file.exists()) {
            Logger.getLogger(IDef.App_Tag).log(Level.WARNING, "Read image ["+filePath+"] failure");
            return (null);
        }

        PlateIDAPI.TH_SetImageFormat(1, 0, 1);
        ConfigArgument configArg = new ConfigArgument();

        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.individual);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.tworowyellow);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.armpolice);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.tworowarmy);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.tractor);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.onlytworowyellow);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.embassy);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.onlylocation);
        PlateIDAPI.TH_SetEnabledPlateFormat(configArg.armpolice2);
        PlateIDAPI.TH_SetRecogThreshold(7, 5);
        PlateIDAPI.TH_SetContrast(9);

        TH_PlateIDResult[] result = PlateIDAPI.TH_RecogImage(file.getAbsolutePath(),
                CarORCCaptureActivity.Desired_Picture_Width, CarORCCaptureActivity.Desired_Picture_Height, localResult,
                array1, 0, 0, 0, 0,array2);

        if( (result == null) || (result.length== 0) ) { //识别失败
            Logger.getLogger(IDef.App_Tag).log(Level.WARNING, "Image recognize failure");
            file.delete();
            return (null);
        }

        StringBuffer sb = new StringBuffer();
        sb.append(result[0].toString() );

        //更名
        File newFile = new File(IDef.App_Dir+File.separatorChar+result[0].getLicense()+".jpg");
        file.renameTo(newFile);
        file.delete();

        return (sb.toString() );
    }
};
