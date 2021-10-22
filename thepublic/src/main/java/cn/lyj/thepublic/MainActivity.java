package cn.lyj.thepublic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.R2;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.tools.CheckUtils;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.ContrlScrollViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lyj.thepublic.user.OwnFragment;

public class MainActivity extends AllenBaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar bar;
    @BindView(R2.id.pager)
    ContrlScrollViewPager pager;
    @BindView(R2.id.bottom)
    BottomNavigationView bottom;
    private FragmentAdapter adapter;
    private List<Fragment> list;
    private SharedPreferences shared;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        actHelper.doClickTwiceExit(pager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
//        bar.setNavigationIcon(R.mipmap.ic_logo_scan);
        setToolbarTitle(bar,getString(R.string.app_name));
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_notice, menu);
//        return true;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shared.getBoolean(Constants.UserIsPeaceman,false)||shared.getBoolean(Constants.UserIsAdmin,false)){
            bottom.getMenu().getItem(2).setVisible(true);
        }else{
            bottom.getMenu().getItem(2).setVisible(false);
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        shared = actHelper.getSharedPreferences();
        list = new ArrayList<>();
        list.add(OwnFragment.init());
        list.add(OwnFragment.init());
        list.add(OwnFragment.init());
        list.add(OwnFragment.init());
        adapter = new FragmentAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(adapter);
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);

                view.setEnabled(true);
            }
        });
        bottom.setOnNavigationItemSelectedListener(listener);
        bottom.setSelectedItemId(R.id.item_serve);
        if(shared.getBoolean(Constants.UserIsPeaceman,false)||shared.getBoolean(Constants.UserIsAdmin,false)){
            bottom.getMenu().getItem(2).setVisible(true);
        }else{
            bottom.getMenu().getItem(2).setVisible(false);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.item_serve) {
                pager.setCurrentItem(0);
            } else if (itemId == R.id.item_news) {
                pager.setCurrentItem(1);
            } else if (itemId == R.id.item_work) {
                pager.setCurrentItem(2);
            } else if (itemId == R.id.item_own) {
                pager.setCurrentItem(3);
            }
            return true;
        }
    };

    private boolean isBuildVersionGreaterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}