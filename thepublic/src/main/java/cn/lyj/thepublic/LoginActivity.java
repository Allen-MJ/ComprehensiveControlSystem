package cn.lyj.thepublic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import allen.frame.AllenIMBaseActivity;
import allen.frame.entry.Response;
import allen.frame.entry.LoginInfo;
import allen.frame.net.Callback;
import allen.frame.net.Http;
import allen.frame.net.Https;
import allen.frame.tools.CheckUtils;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.ClickDrawEditText;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.data.API;

public class LoginActivity extends AllenIMBaseActivity {
    @BindView(R2.id.login_phone)
    AppCompatEditText loginPhone;
    @BindView(R2.id.login_psw)
    ClickDrawEditText loginPsw;
    @BindView(R2.id.login_bt)
    AppCompatButton loginBt;
    @BindView(R2.id.login_regist)
    AppCompatTextView loginRegist;
    private SharedPreferences shared;
    private boolean isToken = false;

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
        Constants.version = 0;
        isToken = getIntent().getBooleanExtra(Constants.Key_Token,false);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        shared = actHelper.getSharedPreferences();
        loginRegist.setText(Html.fromHtml(getString(R.string.login_regist)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Constants.ISDEBUG){
            loginPhone.setText("18580617183");
            loginPsw.setText("iloveyou!134");
        }else{
            loginPhone.setText(shared.getString(Constants.UserPhone,""));
        }
    }

    @Override
    protected void addEvent() {
        loginPsw.setOnClickDrawListenner(new ClickDrawEditText.onClickDrawListenner() {
            @Override
            public void onHide(ClickDrawEditText view) {
                view.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            @Override
            public void onShow(ClickDrawEditText view) {
                view.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });
    }

    @OnClick({R2.id.login_bt, R2.id.login_regist})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.login_bt){
            login();
        }else if(id==R.id.login_regist){
            startActivity(new Intent(context,RegistActivity.class));
        }
        view.setEnabled(true);
    }

    private void login(){
        showProgressDialog("正在验证,请稍等...");
        String phone = loginPhone.getText().toString().trim();
        String psw = loginPsw.getText().toString().trim();
        if(StringUtils.empty(phone)){
            MsgUtils.showMDMessage(this,"请输入手机号!");
            dismissProgressDialog();
            return;
        }
        if(!CheckUtils.phoneIsOk(phone)){
            MsgUtils.showMDMessage(this,"请输入正确的手机号!");
            dismissProgressDialog();
            return;
        }
        if(StringUtils.empty(psw)){
            MsgUtils.showMDMessage(this,"请输入密码!");
            dismissProgressDialog();
            return;
        }
        Https.with(this).url(API._2)
            .addParam("phone",phone)
            .addParam("password",psw)
            .post()
            .enqueue(new Callback<LoginInfo>() {

                @Override
                public void success(LoginInfo data) {
                    dismissProgressDialog();
                    shared.edit().putString(Constants.UserToken,data.getToken())
                            .putString(Constants.UserPhone,data.getUser().getUser().getPhone())
                            .putString(Constants.UserId,data.getUser().getUser().getId())
                            .putString(Constants.UserPhoto,data.getUser().getUser().getAvatarPath())
                            .putString(Constants.UserAddress,data.getUser().getUser().getAddress())
                            .putString(Constants.UserEmail,data.getUser().getUser().getEmail())
                            .putString(Constants.UserName,data.getUser().getUser().getUsername())
                            .putString(Constants.UserGender,data.getUser().getUser().getGender())
                            .putString(Constants.UserNickName,data.getUser().getUser().getNickName())
                            .putString(Constants.UserGrage,data.getUser().getUser().getGrade())
                            .apply();
                    if(isToken){
                        setResult(RESULT_OK,getIntent());
                        finish();
                    }else{
                        startActivity(new Intent(context,HomeActivity.class));
                    }
                }

                @Override
                public void fail(Response response) {
                    dismissProgressDialog();
                    MsgUtils.showMDMessage(context,response.getMsg());
                }
            });
    }
}
