package com.wintone.plateid;

/**
 * 车牌识别配置
 * @author PAUL
 * @email foolstudio@qq.com
 * @since 2015.6
 */
public class TH_PlateIDCfg
{

    public TH_PlateIDCfg()
    {
        nMinPlateWidth = 80;
        nMaxPlateWidth = 200;
        bVertCompress = 0;
        bIsFieldImage = 0;
        bOutputSingleFrame = 0;
        bMovingImage = 0;
        bIsNight = 1;
        nImageFormat = 1;
        nLastError = 0;
        nErrorModelSN = 0;
        reserved = "";
    }

    int bIsFieldImage;
    int bIsNight;
    int bMovingImage;
    int bOutputSingleFrame;
    int bVertCompress;
    int nErrorModelSN;
    int nImageFormat;
    int nLastError;
    int nMaxPlateWidth;
    int nMinPlateWidth;
    String reserved;
}
