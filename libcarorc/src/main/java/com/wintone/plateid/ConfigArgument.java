package com.wintone.plateid;

public class ConfigArgument
{

    public ConfigArgument()
    {
        cfg = "";
        dFormat = 0;
        nPlateLocate_Th = 5;
        nOCR_Th = 2;
        bIsAutoSlope = 1;
        nSlopeDetectRange = 0;
        szProvince = "";
        nContrast = 0;
        individual = 0;
        tworowyellow = 3;
        armpolice = 4;
        tworowarmy = 7;
        tractor = 9;
        onlytworowyellow = 11;
        embassy = 13;
        onlylocation = 15;
        armpolice2 = 17;
        c_Config = new TH_PlateIDCfg();
    }

    public TH_PlateIDCfg getTH_PlateIDCfg()
    {
        return c_Config;
    }

    public static final String TAG = "ConfigArgument";
    public int armpolice;
    public int armpolice2;
    public int bIsAutoSlope;
    public TH_PlateIDCfg c_Config;
    public String cfg;
    public String cfgs[];
    public int dFormat;
    public int embassy;
    public int individual;
    public int nContrast;
    public int nOCR_Th;
    public int nPlateLocate_Th;
    public int nSlopeDetectRange;
    public int onlylocation;
    public int onlytworowyellow;
    public String szProvince;
    public int tractor;
    public int tworowarmy;
    public int tworowyellow;
}
