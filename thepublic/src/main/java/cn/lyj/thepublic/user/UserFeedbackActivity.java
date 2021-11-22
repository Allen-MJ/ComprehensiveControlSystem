package cn.lyj.thepublic.user;

import android.os.Bundle;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;

public class UserFeedbackActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.feed)
    AppCompatEditText feed;
    @BindView(R2.id.commit_bt)
    AppCompatButton commitBt;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_feedback_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"意见反馈",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                finish();
                v.setEnabled(true);
            }
        });
    }

    @OnClick(R2.id.commit_bt)
    public void onViewClicked(View view) {
        view.setEnabled(false);
        feedBack();
        view.setEnabled(true);
    }

    private void feedBack(){
        String msg = feed.getText().toString().trim();
        if(StringUtils.empty(msg)){
            MsgUtils.showMDMessage(context,"请输入意见反馈!");
            return;
        }
        showProgressDialog("正在提交，请稍等...");
        Https.with(this).url(API.FeedBack).addParam("opinionContent",msg).post()
                .enqueue(new Callback<Object>() {
                    @Override
                    public void success(Object data) {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context,"提交成功!");
                        finish();
                    }

                    @Override
                    public void token() {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context,"账号过期,请重新登录!");
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context,response.getMsg());
                    }
                });
    }
}
