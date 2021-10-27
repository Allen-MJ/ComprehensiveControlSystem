package cn.lyj.thepublic.square;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.MsgUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;

public class DiscussActivity extends AllenBaseActivity {
    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.et_discuss_info)
    AppCompatEditText etDiscussInfo;
    @BindView(R2.id.ok_bt)
    AppCompatButton okBt;
    private SharedPreferences shared;


    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_discuss;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        setToolbarTitle(toolbar, getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MsgUtils.showMDMessage(context, "是否退出事件上报?", "确定", new DialogInterface.OnClickListener() {
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

    @OnClick({R2.id.ok_bt})
    public void onViewClicked(View view) {
        int id=view.getId();
        if (id==R.id.ok_bt){

        }
    }
}
