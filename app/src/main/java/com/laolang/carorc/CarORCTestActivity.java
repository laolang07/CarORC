package com.laolang.carorc;

import android.os.Bundle;

import com.laolang.libcarorc.CarORCCaptureActivity;

public class CarORCTestActivity extends CarORCCaptureActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initCarORCAPI();//初始化API
        super.onCreate(savedInstanceState);
    }
}
