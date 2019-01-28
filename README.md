# CarORC
###Android扫车牌号识别技术lib项目

### 1. 整牌识别率高达99.7%，兼容汉字识别识别； 　　
### 2. 识别速度快，极致优化的车牌定位和识别算法;
### 3. 使用起来非常方便直接继承lib中的CarORCCaptureActivity
**【重要】**需要在activity onCreate之前进行API初始化，不然识别会出现错误
 
 

 
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
        return R.layout.activity_carorccapture;
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
    

### 4. 测试机效果图
![CarORCDemo](http://m.qpic.cn/psb?/V13dxhH52XJVF2/0kIPwJuvdkw6pYGQAk1Gz1*MzhtF0.*jP04oyf6EQQI!/b/dLwAAAAAAAAA&bo=SwG7AgAAAAARB8M!&rf=viewer_4)

