package cn.lyj.work;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.widget.CircleImageView;
import allen.frame.widget.ContrlScrollViewPager;
import allen.frame.widget.VerticalTextview;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.core.LocationService;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.SystemNotice;
import cn.lyj.user.notice.SystemNoticeActivity;

public class HomeActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R2.id.user_name)
    AppCompatTextView userName;
    @BindView(R2.id.user_grade)
    AppCompatTextView userGrade;
    @BindView(R2.id.user_dw)
    AppCompatTextView userDw;
    @BindView(R2.id.xl_bt)
    AppCompatImageView xlBt;
    @BindView(R2.id.pager)
    ContrlScrollViewPager pager;
    @BindView(R2.id.bottom)
    BottomNavigationView bottom;
    @BindView(R2.id.notice)
    VerticalTextview notice;
    private FragmentAdapter adapter;
    private List<Fragment> list;
    private boolean isXl = false;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.work_home_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this)
                .load(Constants.url + shared.getString(Constants.UserPhoto, ""))
                .error(R.drawable.default_photo).placeholder(R.drawable.default_photo)
                .into(userPhoto);
        isXl = shared.getBoolean(Constants.UserMap, false);
        xlBt.setImageResource(isXl ? R.drawable.lamp_green : R.drawable.lamp_red);
        notice();
    }

    @Override
    protected void onPause() {
        super.onPause();
        notice.stopAutoScroll();
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        userName.setText(shared.getString(Constants.UserName, ""));
        userGrade.setText(shared.getString(Constants.UserRoleName, ""));
        userDw.setText(shared.getString(Constants.UserUnitsName, ""));
        list = new ArrayList<>();
        list.add(HomeFragment.init());
        list.add(ModelFragment.init(1));
        list.add(ModelFragment.init(2));
        adapter = new FragmentAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(adapter);
        notice.setAnimTime(300);
        notice.setTextStillTime(2000);
    }

    @Override
    protected void addEvent() {
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.item_home) {
                    pager.setCurrentItem(0);
                } else if (itemId == R.id.item_manager) {
                    pager.setCurrentItem(1);
                } else if (itemId == R.id.item_work) {
                    pager.setCurrentItem(2);
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.item_home);
        notice.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(context, SystemNoticeActivity.class));
            }
        });
    }

    @OnClick({R2.id.user_photo, R2.id.xl_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == R.id.xl_bt) {
            requestRunPermisssion(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10, new PermissionListener() {
                @Override
                public void onGranted(int requestCode) {
                    Intent locationIntent = new Intent(HomeActivity.this, LocationService.class);
                    isXl = shared.getBoolean(Constants.UserMap, false);
                    shared.edit().putBoolean(Constants.UserMap, !isXl).apply();
                    if (isXl) {
                        stopService(locationIntent);
                    } else {
                        //开启前台服务
                        startService(locationIntent);
                    }
                    xlBt.setImageResource(!isXl ? R.drawable.lamp_green : R.drawable.lamp_red);
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    MsgUtils.showMDMessage(context, "巡逻功能需要开通权限!");
                }
            });
        }
        view.setEnabled(true);
    }

    private void notice(){
        Https.with(this).url(CoreApi.systemNotice).addParam("page",0).addParam("size",10).get()
                .enqueue(new Callback<List<SystemNotice>>() {
                    @Override
                    public void success(List<SystemNotice> data) {
                        if(data!=null&&data.size()>0){
                            ArrayList<String> texts = new ArrayList<>();
                            for(SystemNotice entry:data){
                                texts.add(entry.getNoticeTitle());
                            }
                            notice.setTextList(texts);
                            notice.startAutoScroll();
                        }else{
                            MsgUtils.showShortToast(context,"暂无通知!");
                            ArrayList<String> texts = new ArrayList<>();
                            texts.add("暂无通知!");
                            notice.setTextList(texts);
                            notice.startAutoScroll();
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showShortToast(context,response.getMsg());
                    }
                });
    }
}
