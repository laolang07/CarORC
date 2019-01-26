package com.wintone.plateid;


import com.wintone.plate.Package;

/**
 * 车牌识别API
 * @author PAUL
 * @email foolstudio@qq.com
 * @since 2015.6
 */
public class PlateIDAPI {
    static
    {
        System.loadLibrary("CWImage");
        System.loadLibrary("THPlateIDFree");
    }

    //返回车牌识别库版本。 格式： 主版本号 .副版本号  .修订号 .编译号  。
    public static native String TH_GetVersion();

    /**
     * 初始化识别库
     * @param paramTH_PlateIDCfg
     * @param paramPackage 车牌识别SDK的配置
     * @return
     */
    public static native int TH_InitPlateIDSDK(
            TH_PlateIDCfg paramTH_PlateIDCfg,
            Package paramPackage);

    public static native int TH_InitPlateIDSDKTF(
            TH_PlateIDCfg paramTH_PlateIDCfg);

    public static native TH_PlateIDResult[] TH_RecogImage(
            String picturePath, //picturePath
            int width,  //宽度
            int height,  //高度
            TH_PlateIDResult result,
            int[] paramArrayOfInt1, //{10}
            int paramInt3, //0
            int paramInt4, //0
            int paramInt5, //0
            int paramInt6, //0
            int[] paramArrayOfInt2); //{-1}

    public static native TH_PlateIDResult[] TH_RecogImageByte(
            byte[] paramArrayOfByte,
            int paramInt1,
            int paramInt2,
            TH_PlateIDResult paramTH_PlateIDResult,
            int[] paramArrayOfInt1,
            int paramInt3,
            int paramInt4,
            int paramInt5,
            int paramInt6,
            int[] paramArrayOfInt2);

    public static native int TH_SetAutoSlopeRectifyMode(
            int paramInt1,
            int paramInt2);

    public static native int TH_SetContrast(
            int paramInt); //9

    //设置夜间模式
    public static native int TH_SetDayNightMode(
            int paramInt);

    //设置对特殊车牌的识别
    public static native int TH_SetEnabledPlateFormat(
            int paramInt);

    public static native int TH_SetEnlargeMode(
            int paramInt);

    //设置图像格式
    public static native int TH_SetImageFormat(
            int imageFormat, //图像格式
            int paramInt2,   //是否上下颠倒
            int paramInt3);

    //设置默认省份
    public static native int TH_SetProvinceOrder(
            String paramString);

    //设置识别阈值
    public static native int TH_SetRecogThreshold(
            int paramInt1,  //7
            int paramInt2); //5

    public static native int TH_UninitPlateIDSDK();
}
