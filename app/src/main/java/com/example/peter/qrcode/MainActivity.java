package com.example.peter.qrcode;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.qrcode.activity.CustomScanActivity;
import com.example.peter.qrcode.base.BaseActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.activity_main_tv_content)
    TextView tv_content;
    @InjectView(R.id.activity_main_tv_scan)
    TextView tv_scan;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            // Enter here by scanning code.
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                tv_content.setVisibility(View.VISIBLE);
                String ScanResult = intentResult.getContents();
                tv_content.setText(ScanResult);
                Toast.makeText(this, ScanResult, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.activity_main_tv_scan)
    public void scan(){
        customScan();
    }

    public void customScan() {
        new IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
//                .setPrompt("将二维码/条码放入框内，即可自动扫描test")
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
