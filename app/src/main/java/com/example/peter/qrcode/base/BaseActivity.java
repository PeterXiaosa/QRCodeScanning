package com.example.peter.qrcode.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * Created by Peter on 2017/12/14.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener, BaseViewInterface{

    private boolean _isVisible;

    private ProgressDialog waitDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        // 通过注解绑定控件
        ButterKnife.inject(this);


        initView();
        initData();

        _isVisible = true;
    }

    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected String jsonTokener(String in) {
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }

//    protected void showDialog() {
//        if (waitDialog == null) {
//            waitDialog = DialogHelp.getWaitDialog(this, getString(R.string.saving));
//        }
//        waitDialog.setCancelable(false);
//        waitDialog.setCanceledOnTouchOutside(false);
//        waitDialog.show();
//    }

//    protected void showDialog(String msg) {
//        if (waitDialog == null) {
//            waitDialog = DialogHelp.getWaitDialog(this, msg);
//        }
//        waitDialog.show();
//    }

    protected void hideDialog() {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
    }

    public void use(Method method)
    {
        Class<?> clazz = this.getClass();
        try {
//            method.invoke(clazz.newInstance());
            method.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
