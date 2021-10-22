package cn.lyj.thepublic;

import android.os.Bundle;

import allen.frame.AllenIMBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.DataHttp;
import androidx.annotation.Nullable;

import allen.frame.net.Http;
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
            public void success(String data) {

            }

            @Override
            public void fail(Response response) {

            }
        });

        Http.with(this).url(API._1).parameters(new Object[]{}).enqueue(new Callback<String>() {
            @Override
            public void success(String data) {

            }

            @Override
            public void fail(Response response) {

            }
        }).post();
    }

    @Override
    protected void addEvent() {

    }
}
