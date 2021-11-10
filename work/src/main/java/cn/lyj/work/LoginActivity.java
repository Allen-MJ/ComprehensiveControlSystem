package cn.lyj.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import allen.frame.AllenIMBaseActivity;
import allen.frame.entry.LoginInfo;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.EncryptUtils;
import allen.frame.tools.ImageUtils;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.ClickDrawEditText;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.LoginAuth;

public class LoginActivity extends AllenIMBaseActivity {
    @BindView(cn.lyj.core.R2.id.login_account)
    AppCompatEditText loginAccount;
    @BindView(cn.lyj.core.R2.id.login_psw)
    ClickDrawEditText loginPsw;
    @BindView(cn.lyj.core.R2.id.login_bt)
    AppCompatButton loginBt;
    @BindView(cn.lyj.core.R2.id.login_yzm)
    AppCompatEditText loginYzm;
    @BindView(cn.lyj.core.R2.id.yzm)
    AppCompatImageView yzm;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return cn.lyj.core.R.layout.core_login_layout;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        authCode();
    }

    @Override
    protected void addEvent() {

    }

    @OnClick({cn.lyj.core.R2.id.login_bt, R2.id.yzm})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == cn.lyj.core.R.id.login_bt) {
            login();
        }else if(id == cn.lyj.core.R.id.yzm){
            authCode();
        }
        view.setEnabled(true);
    }
    private String uuid = "";
    private void authCode(){
        Https.with(this).url(CoreApi.authCode).get()
                .enqueue(new Callback<LoginAuth>() {
                    @Override
                    public void success(LoginAuth data) {
                        if(data!=null){
                            yzm.setImageBitmap(ImageUtils.base64ToBitmap(data.getImg()));
                            uuid = data.getUuid();
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showShortToast(context,response.getMsg());
                    }
                });
    }

    private void login(){
        String account = loginAccount.getText().toString().trim();
        String psw = loginPsw.getText().toString().trim();
        String yzmCode = loginYzm.getText().toString().trim();
        if(StringUtils.empty(account)){
            MsgUtils.showMDMessage(context,"请输入账号!");
            return;
        }
        if(StringUtils.empty(psw)){
            MsgUtils.showMDMessage(context,"请输入密码!");
            return;
        }
        if(StringUtils.empty(uuid)){
            MsgUtils.showMDMessage(context,"请先获取验证码!");
            return;
        }
        if(StringUtils.empty(yzmCode)){
            MsgUtils.showMDMessage(context,"请输入验证码!");
            return;
        }
        showProgressDialog("");
        Https.with(this).url(CoreApi.authLogin)
                .addParam("code",yzmCode).addParam("username",account)
                .addParam("password", EncryptUtils.rsaEncrypt(psw, Constants.publicKey))
                .addParam("uuid",uuid).post()
                .enqueue(new Callback<LoginInfo>() {
                    @Override
                    public void success(LoginInfo data) {
                        dismissProgressDialog();
                        shared.edit().putString(Constants.UserToken,data.getToken())
                                .putString(Constants.UserPhone,data.getUser().getUser().getPhone())
                                .putString(Constants.UserId,data.getUser().getUser().getId())
                                .putString(Constants.UserAddress,data.getUser().getUser().getAddress())
                                .putString(Constants.UserEmail,data.getUser().getUser().getEmail())
                                .putString(Constants.UserName,data.getUser().getUser().getUsername())
                                .putString(Constants.UserGender,data.getUser().getUser().getGender())
                                .putString(Constants.UserNickName,data.getUser().getUser().getNickName())
                                .putString(Constants.UserGrage,data.getUser().getUser().getGrade()).apply();
                        startActivity(new Intent(context,HomeActivity.class));
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }

}
