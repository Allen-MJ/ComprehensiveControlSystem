package cn.lyj.core.task;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.TaskEntity;

public class FeedActivity extends AllenBaseActivity {
    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.et_discuss_info)
    AppCompatEditText etDiscussInfo;
    @BindView(R2.id.ok_bt)
    AppCompatButton okBt;
    private SharedPreferences shared;
    private TaskEntity taskEntity;


    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_feed;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "任务反馈",true);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        shared = actHelper.getSharedPreferences();
        taskEntity= (TaskEntity) getIntent().getSerializableExtra(Constants.Key_1);
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MsgUtils.showMDMessage(context, "是否退出任务反馈?", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        finish();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void feed(){
        Https.with(this).url(CoreApi._task_feed).addParam("taskid",taskEntity.getTaskId()).post()
                .addParam("feedback",feed).post().enqueue(new Callback() {
            @Override
            public void success(Object data) {
                MsgUtils.showLongToast(context,"提交成功!");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void token() {
                MsgUtils.showShortToast(context,"账号登录过期,请重新登录!");
            }
            @Override
            public void fail(Response response) {
                MsgUtils.showShortToast(context,response.getMsg());
            }
        });
    }

    private String feed;
    @OnClick({R2.id.ok_bt})
    public void onViewClicked(View view) {
        int id=view.getId();
        if (id== R.id.ok_bt){
            feed=etDiscussInfo.getText().toString().trim();
            if (StringUtils.empty(feed)){
                MsgUtils.showMDMessage(context,"请先填写反馈内容!");
                return;
            }
            feed();
        }
    }
}
