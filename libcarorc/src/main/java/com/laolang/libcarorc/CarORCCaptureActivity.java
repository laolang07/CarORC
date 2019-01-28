package com.laolang.libcarorc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.laolang.IDef;
import com.laolang.RecognizeThread;
import com.laolang.util.FooSysUtil;
import com.laolang.util.PermissionUtils;
import com.wintone.plate.FreePlatePackage;
import com.wintone.plate.Package;
import com.wintone.plateid.PlateIDAPI;
import com.wintone.plateid.TH_PlateIDCfg;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CarORCCaptureActivity extends AppCompatActivity implements View.OnClickListener,
        SurfaceHolder.Callback, PictureCallback, AutoFocusCallback {
    public static int Desired_Preview_Width = 1280;
    public static int Desired_Preview_Height = 720;
    public static int Desired_Picture_Width = 1280;
    public static int Desired_Picture_Height = 960;

    private SurfaceView mVideoView = null;
    private SurfaceHolder mHolder = null;
    private Camera mCamera = null;
    //
    private ImageView mBtnCapture = null;

    //超时计时
    private Timer mTimer = null;
    private Handler mMsgHandler = null;
    private int mCounter = 0;

    //对焦超时为2秒
    private static final int Timeout = 2;
    private TextView tv = null;
    private ProgressBar btn_wait = null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (findViewById(R.id.textView1) != null)
            tv = findViewById(R.id.textView1);

        if (findViewById(R.id.btn_wait) != null)
            btn_wait = findViewById(R.id.btn_wait);

        //获取按钮控件并设置点击侦听
        if (findViewById(R.id.btn_capture) != null) {
            mBtnCapture = findViewById(R.id.btn_capture);
            mBtnCapture.setOnClickListener(this);
        }
        //获取视频渲染视图
        this.mVideoView = findViewById(R.id.preview_view);
        this.mHolder = this.mVideoView.getHolder();
        this.mHolder.addCallback(this);
        //this.mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //初始化主线程消息队列处理器接口
        mMsgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == IDef.Msg_Tick) {
                    mCounter++;
                    if (mCounter >= Timeout) {
                        timerTimeout();
                    }
                }
                if (msg.what == IDef.Msg_Recognized_Finished) {
                    recognizedFinished(msg.getData());
                }
            }
        };
    }

    public int getLayoutId() {
        return R.layout.activity_carorccapture;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.CODE_REQUEST_PERMISSION) {
            if (PermissionUtils.HavecheckPermission(CarORCCaptureActivity.this)) {
                //初始化照相机
                openCamera();
                startPreview();
            }

        }
    }

    @Override
    protected void onDestroy() {

        //取消超时计时
        stopTimer();

        //停止预览，释放资源
        if (this.mCamera != null) {
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }

        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (PermissionUtils.checkPermission(this)) {
            //初始化照相机
            openCamera();
            startPreview();
        }
    }

    private void openCamera() {
        try { //确认照相机处于关闭状态
            closeCamera();
            //打开照相机
            this.mCamera = Camera.open();
            this.mCamera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
            //获取参数，设置参数
            Camera.Parameters params = this.mCamera.getParameters();

            //获取所支持的大小 @2015-06-30 兼容性解决方案
            getSupportedPreviewSize(params);
            getSupportedPictureSize(params);

            params.setPictureFormat(ImageFormat.JPEG);
            params.setPreviewSize(Desired_Preview_Width, Desired_Preview_Height);
            //设置场景
            params.setPictureSize(Desired_Picture_Width, Desired_Picture_Height);
            //@2015-06-30 兼容性解决方案
            if (Build.VERSION.SDK_INT >= 8) {
                this.mCamera.setDisplayOrientation(90);
            } else {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    params.set("orientation", "portrait");
                    params.set("rotation", 90);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    params.set("orientation", "landscape");
                    params.set("rotation", 90);
                }
            }

            //设置白平衡
            //params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_FLUORESCENT);

            this.mCamera.setParameters(params);
            this.mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取支持的预览大小
    private void getSupportedPreviewSize(Camera.Parameters params) {

        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Comparator<Camera.Size> comparator = new Comparator<Camera.Size>() {

            @Override
            public int compare(Camera.Size arg1, Camera.Size arg2) {

                //倒序
                if (arg2.width > arg1.width) {
                    return (1);
                } else if (arg2.width == arg1.width) {
                    if (arg2.height > arg1.height) {
                        return (1);
                    } else if (arg2.height == arg1.height) {
                        return (0);
                    } else {
                        return (-1);
                    }
                } else {
                    return (-1);
                }
            }

        };
        Collections.sort(sizes, comparator);

        boolean isGot = false;
        for (int i = 0; i < sizes.size(); ++i) {

            Camera.Size tSize = sizes.get(i);
            if ((tSize.width == Desired_Preview_Width) && !isGot) { //1280x
                Desired_Preview_Height = tSize.height;
                isGot = true;
            }
            ;
        }
        if (isGot == false) { //没有匹配上
            Camera.Size tSize = sizes.get(sizes.size() / 2);
            Desired_Preview_Width = tSize.width;
            Desired_Preview_Height = tSize.height;
        }

    }

    //获取支持的图片大小
    private void getSupportedPictureSize(Camera.Parameters params) {

        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Comparator<Camera.Size> comparator = new Comparator<Camera.Size>() {

            @Override
            public int compare(Camera.Size arg1, Camera.Size arg2) {

                //倒序
                if (arg2.width > arg1.width) {
                    return (1);
                } else if (arg2.width == arg1.width) {
                    if (arg2.height > arg1.height) {
                        return (1);
                    } else if (arg2.height == arg1.height) {
                        return (0);
                    } else {
                        return (-1);
                    }
                } else {
                    return (-1);
                }
            }

        };
        Collections.sort(sizes, comparator);

        StringBuffer sb = new StringBuffer();
        boolean isGot = false;
        for (int i = 0; i < sizes.size(); ++i) {

            Camera.Size tSize = sizes.get(i);

            sb.append("(");
            sb.append(tSize.width);
            sb.append(",");
            sb.append(tSize.height);
            sb.append(") ");

            if ((tSize.width == Desired_Picture_Width) && !isGot) { //1280x
                Desired_Picture_Height = tSize.height;
                isGot = true;
            }
        }
        if (isGot == false) { //没有匹配上
            Camera.Size tSize = sizes.get(sizes.size() / 2);
            Desired_Picture_Width = tSize.width;
            Desired_Picture_Height = tSize.height;
        }

    }

    private void closeCamera() {
        if (this.mCamera == null) {
            return;
        }
        try { // 使照相机重新获取资源的控制
            this.mCamera.reconnect();
            // 停止预览，释放资源
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void startPreview() {
        // 先使照相机处于停止预览状态
        this.mCamera.stopPreview();
        // 开始预览
        this.mCamera.startPreview();
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {

            //停止聚焦超时计时器
            stopTimer();

            //startPreview();
            camera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
            //camera.autoFocus(null);
            camera.takePicture(null, null, this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_capture) {
            TakePhoto();
        }
    }

    /**
     * 进行拍照
     */
    protected void TakePhoto() {
        if (this.mCamera != null) {
            //startPreview();
            this.mCamera.autoFocus(this);
            //隐藏按钮
            if (mBtnCapture != null)
                mBtnCapture.setVisibility(View.INVISIBLE);

            if (btn_wait != null)
                btn_wait.setVisibility(View.VISIBLE);
            StartScaning("采集车牌信息，请持稳设备...");
            //启动超时计时器
            startTimer();
        }
    }

    /**
     * 相机拍照声音
     */
    private MediaPlayer shootMP = null;

    public void shootSound() {
        AudioManager meng = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volume = meng.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        if (volume != 0) {
            if (shootMP == null)
                shootMP = MediaPlayer.create(this, Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
            if (shootMP != null)
                shootMP.start();
        }
    }

    /**
     * 相机拍照声音
     */
    private MediaPlayer camera_focus = null;

    public void focusSound() {
        AudioManager meng = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volume = meng.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        if (volume != 0) {
            if (camera_focus == null)
                camera_focus = MediaPlayer.create(this, Uri.parse("file:///system/media/audio/ui/camera_focus.ogg"));
            if (camera_focus != null)
                camera_focus.start();
        }
    }

    //自动对焦超时计时器开始计时
    private void startTimer() {
        focusSound();
        this.mTimer = new Timer();
        this.mCounter = 0;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mMsgHandler.sendEmptyMessage(IDef.Msg_Tick);
            }
        };
        //规划任务执行的时点和频率
        this.mTimer.scheduleAtFixedRate(task, 0, 1000L);
    }

    //停止聚焦超时计时器
    private void stopTimer() {
        //取消超时计时
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        shootSound();
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        File dir = new File(IDef.App_Dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final String filePath = IDef.App_Dir +
                FooSysUtil.getInstance().getTimeStr("") + ".jpg";
        File f = new File(filePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            //拍完照之后会停止预览，需要恢复预览
            //startPreview();
            returnResult(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnResult(String filePath) {
        if (filePath != null) {
            startRecognize(filePath);
        }
    }

    private String ScanResutl = null;

    public String getScanResutl() {
        return ScanResutl;
    }

    /**
     * 开始进行拍照解析
     *
     * @param msg
     */
    protected void StartScaning(String msg) {
        if (tv != null)
            tv.setText(msg);
    }

    /**
     * 拍照解析结束
     *
     * @param iSsuccess
     * @param msg
     * @param result
     */
    protected void Scanfinished(boolean iSsuccess, String msg, String result) {
        if (iSsuccess) {
            if (tv != null)
                tv.setText(result);
        } else {
            if (tv != null)
                tv.setText(msg);
        }
    }

    //识别结束
    protected void recognizedFinished(Bundle bundle) {
        if (mBtnCapture != null)
            mBtnCapture.setVisibility(View.VISIBLE);
        if (btn_wait != null)
            btn_wait.setVisibility(View.GONE);
        startPreview();
        if (bundle == null) {
            ScanResutl = null;
            Scanfinished(false, "识别失败，请再试", ScanResutl);
            return;
        }

        String result = bundle.getString(IDef.Extra_Recog_Result);
        if (result == null) {
            ScanResutl = null;
            Scanfinished(false, "识别失败，请再试", ScanResutl);
            return;
        }

        String arr[] = result.split("룺");
        if (arr != null && arr.length == 2 && arr[1] != null) {
            ScanResutl = arr[1];
            Scanfinished(true, "识别成功", ScanResutl);
        } else {
            ScanResutl = result;
            Scanfinished(true, "识别成功", ScanResutl);
        }
    }

    //开始识别
    private void startRecognize(String filePath) {
        // TODO Auto-generated method stub
        //tv.setText("正在识别，请稍候……");
        RecognizeThread t = new RecognizeThread(this.mMsgHandler, filePath);
        t.start();
    }

    //自动对焦超时
    protected void timerTimeout() {
        //停止聚焦超时计时器
        stopTimer();
        if (this.mCamera != null) {
            this.mCamera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
            //camera.autoFocus(null);
            this.mCamera.takePicture(null, null, this);
        }
    }

    /**
     * //初始化车牌识别SDK
     */
    protected void initCarORCAPI() {
        //设置包名
        FreePlatePackage.FreePlatePackageName = "com.wintone.plateid.free"; //getPackageName();
        //初始化车牌识别SDK，包名必须保证为：com.wintone.plateid.free
        TH_PlateIDCfg cfg = new TH_PlateIDCfg();
        Object obj = new Package();
        int result = PlateIDAPI.TH_InitPlateIDSDK(cfg, (Package) obj);
    }
}
