package cn.lyj.thepublic;

import android.os.Bundle;
import android.view.View;

import allen.frame.AllenIMBaseActivity;
import allen.frame.entry.LoginInfo;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.CheckUtils;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.data.API;

public class RegistActivity extends AllenIMBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.regist_phone)
    AppCompatEditText registPhone;
    @BindView(R2.id.regist_psw)
    AppCompatEditText registPsw;
    @BindView(R2.id.regist_apsw)
    AppCompatEditText registApsw;
    @BindView(R2.id.regist_bt)
    AppCompatButton registBt;

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_regist_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"账号注册",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R2.id.regist_bt)
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.regist_bt){
            regist();
        }
        view.setEnabled(true);
    }

    private void regist(){
        String phone = registPhone.getText().toString().trim();
        String psw = registPsw.getText().toString().trim();
        String apsw = registApsw.getText().toString().trim();
        if(!CheckUtils.phoneIsOk(phone)){
            MsgUtils.showMDMessage(context,"请输入正确的手机号!");
            return;
        }
        if(StringUtils.empty(psw)){
            MsgUtils.showMDMessage(context,"请输入密码!");
            return;
        }
        if(!CheckUtils.passWordIsNotEasy(psw)){
            MsgUtils.showMDMessage(context,"密码强度太低!");
            return;
        }
        if(StringUtils.empty(apsw)){
            MsgUtils.showMDMessage(context,"请确认密码!");
            return;
        }
        if(!apsw.equals(psw)){
            MsgUtils.showMDMessage(context,"两次密码输入不一致!");
            return;
        }
        showProgressDialog("正在验证,请稍等...");
        Https.with(this).url(API._1)
            .addParam("phone",phone)
            .addParam("password",psw)
            .post()
            .enqueue(new Callback<LoginInfo>() {
                @Override
                public void success(LoginInfo data) {
                    dismissProgressDialog();
                    MsgUtils.showShortToast(context,"注册成功!");
                    finish();
                }

                @Override
                public void fail(Response response) {
                    dismissProgressDialog();
                    MsgUtils.showMDMessage(context,response.getMsg());
                }
            });
    }
}
