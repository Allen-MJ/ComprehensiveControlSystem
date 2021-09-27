package cn.lyj.thepublic;

import android.os.Bundle;

import allen.frame.AllenIMBaseActivity;
import androidx.annotation.Nullable;

public class LoginActivity extends AllenIMBaseActivity {
    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_login_layout;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }
}
