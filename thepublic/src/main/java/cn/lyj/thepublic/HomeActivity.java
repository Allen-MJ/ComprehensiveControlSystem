package cn.lyj.thepublic;

import android.os.Bundle;

import allen.frame.AllenIMBaseActivity;
import androidx.annotation.Nullable;

public class HomeActivity extends AllenIMBaseActivity {
    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_home_layout;
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
