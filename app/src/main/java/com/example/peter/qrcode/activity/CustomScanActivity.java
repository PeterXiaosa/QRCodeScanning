package com.example.peter.qrcode.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.peter.qrcode.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import butterknife.OnClick;

/**
 * Created by Peter on 2017/12/14.
 */

public class CustomScanActivity extends Activity implements DecoratedBarcodeView.TorchListener, View.OnClickListener { // 实现相关接口
    // 添加一个按钮用来控制闪光灯，同时添加两个按钮表示其他功能，先用Toast表示

    Button swichLight;
    Button hint1Show;
    Button hint2Show;
    DecoratedBarcodeView mDBV;
    ImageView iv_back;

    private CaptureManager captureManager;
    private boolean isLightOn = false;

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);

        swichLight = (Button)findViewById(R.id.btn_switch);
        hint1Show = (Button) findViewById(R.id.btn_hint1);
        hint2Show = (Button) findViewById(R.id.btn_hint2);
        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
        iv_back = (ImageView) findViewById(R.id.activity_retail_and_scan_scan_ig_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDBV.setTorchListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if(!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }

        swichLight.setOnClickListener(this);

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this,mDBV);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
    }

    // torch 手电筒
    @Override
    public void onTorchOn() {
        Toast.makeText(this,"torch on", Toast.LENGTH_LONG).show();
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        Toast.makeText(this,"torch off",Toast.LENGTH_LONG).show();
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    // 点击切换闪光灯
    @OnClick(R.id.btn_switch)
    public void swichLight(){
        if(isLightOn){
            mDBV.setTorchOff();
        }else{
            mDBV.setTorchOn();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_switch:
                if(isLightOn){
                    mDBV.setTorchOff();
                }else{
                    mDBV.setTorchOn();
                }
                break;
            default:
                break;
        }

    }
}
