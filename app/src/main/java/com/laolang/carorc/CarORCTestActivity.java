package com.laolang.carorc;

import android.os.Bundle;

import com.laolang.libcarorc.CarORCCaptureActivity;

public class CarORCTestActivity extends CarORCCaptureActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initCarORCAPI();//初始化API
        super.onCreate(savedInstanceState);
    }

    /**
     * 如果进行重新布局注意SurfaceViewID和com.laolang.libcarorc.ViewfinderViewID必须和原布局保持一致，其余根据实际功能需求使用
     * @return
     */
    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }

    /**
     * 开始采集图片进行解析
     * @param msg
     */
    @Override
    protected void StartScaning(String msg) {
        super.StartScaning(msg);
    }

    /**
     * 解析结果
     * @param iSsuccess
     * @param msg
     * @param result
     */
    @Override
    protected void Scanfinished(boolean iSsuccess, String msg, String result) {
        super.Scanfinished(iSsuccess, msg, result);

    }
}
