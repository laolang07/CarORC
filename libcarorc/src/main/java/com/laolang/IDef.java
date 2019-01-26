package com.laolang;

public interface IDef {

    public static final String App_Tag = "libCarORC";

    public static final int Req_Code_Camera = 8670;

    public static final String Extra_File_Path = "_file_path";
    public static final String Extra_Recog_Result = "_recog_result";

    public static final int Default_Width = 640;
    public static final int Default_Height = 480;

    //环境配置项
    public static final String App_Dir = "/sdcard/"+IDef.App_Tag+"/images/";

    //消息类型标识
    public static final int Msg_Type_Unknown = -1;
    public static final int Msg_Recognized_Finished = 137;
    public static final int Msg_Net_Ready = 101;
    public static final int Msg_Data_Upd = 103;
    public static final int Msg_Empty = 107;
    public static final int Msg_Tick = 123;
};
