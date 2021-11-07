package cn.lyj.thepublic.user;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import allen.frame.AllenBaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;

public class UserNewsActivity extends AllenBaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;


    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_user_news;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        setToolbarTitle(toolbar, getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }
}