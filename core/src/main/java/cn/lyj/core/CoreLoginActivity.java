package cn.lyj.core;

import android.os.Bundle;
import android.view.View;

import allen.frame.AllenIMBaseActivity;
import allen.frame.widget.ClickDrawEditText;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.OnClick;

public class CoreLoginActivity extends AllenIMBaseActivity {
    @BindView(R2.id.login_account)
    AppCompatEditText loginAccount;
    @BindView(R2.id.login_psw)
    ClickDrawEditText loginPsw;
    @BindView(R2.id.login_bt)
    AppCompatButton loginBt;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_login_layout;
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

    @OnClick(R2.id.login_bt)
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.login_bt){

        }
        view.setEnabled(true);
    }
}
