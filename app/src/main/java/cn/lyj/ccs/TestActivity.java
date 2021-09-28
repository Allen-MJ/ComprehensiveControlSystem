package cn.lyj.ccs;

import android.os.Bundle;
import android.view.View;

import allen.frame.AllenBaseActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends AllenBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.into_1)
    AppCompatButton into1;
    @BindView(R.id.into_2)
    AppCompatButton into2;
    @BindView(R.id.into_3)
    AppCompatButton into3;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.test_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"测试界面",false);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }

    @Override
    public void onBackPressed() {
        actHelper.doClickTwiceExit(toolbar);
    }

    @OnClick({R.id.into_1, R.id.into_2, R.id.into_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.into_1:
                break;
            case R.id.into_2:
                break;
            case R.id.into_3:
                break;
        }
    }
}
