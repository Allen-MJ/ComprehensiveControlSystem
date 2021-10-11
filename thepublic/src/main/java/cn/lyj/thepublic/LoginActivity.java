package cn.lyj.thepublic;

import android.os.Bundle;

import allen.frame.AllenIMBaseActivity;
import allen.frame.net.Callback;
import allen.frame.net.DataHttp;
import androidx.annotation.Nullable;
import cn.lyj.thepublic.data.API;

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
        new DataHttp().add(API._1, new Object[]{"phone", "18580617183", "password", "123456"}, new Callback<String>() {
            @Override
            protected void success(String data) {

            }

            @Override
            protected void fail(Class<?> data) {

            }
        });
    }

    @Override
    protected void addEvent() {

    }
}
