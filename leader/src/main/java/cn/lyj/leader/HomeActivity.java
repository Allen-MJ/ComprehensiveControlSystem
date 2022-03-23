package cn.lyj.leader;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
import allen.frame.tools.StringUtils;
import allen.frame.widget.CircleImageView;
import allen.frame.widget.ContrlScrollViewPager;
import allen.frame.widget.SearchView;
import allen.frame.widget.VerticalTextview;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lyj.core.SearchListActivity;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.SystemNotice;
import cn.lyj.user.notice.SystemNoticeActivity;

public class HomeActivity extends AllenBaseActivity {

    @BindView(R2.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R2.id.user_name)
    AppCompatTextView userName;
    @BindView(R2.id.user_grade)
    AppCompatTextView userGrade;
    @BindView(R2.id.user_dw)
    AppCompatTextView userDw;
    @BindView(R2.id.pager)
    ContrlScrollViewPager pager;
    @BindView(R2.id.bottom)
    BottomNavigationView bottom;
    @BindView(R2.id.notice)
    VerticalTextview notice;
    @BindView(R2.id.search)
    SearchView search;
    private FragmentAdapter adapter;
    private List<Fragment> list;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.leader_home_layout;
    }

    @Override
    protected void initBar() {
        list = new ArrayList<>();
        list.add(ModelFragment.init(3));
        list.add(ModelFragment.init(0));
        list.add(ModelFragment.init(1));
        list.add(ModelFragment.init(2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this)
                .load(Constants.url + shared.getString(Constants.UserPhoto, ""))
                .error(R.drawable.default_photo).placeholder(R.drawable.default_photo)
                .into(userPhoto);
        notice();
//        notice.startAutoScroll();
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
        adapter = new FragmentAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        notice.setAnimTime(300);
        notice.setTextStillTime(2000);
    }

    @Override
    protected void addEvent() {
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.item_tj) {
                    pager.setCurrentItem(0);
                } else if (itemId == R.id.item_sth) {
                    pager.setCurrentItem(1);
                } else if (itemId == R.id.item_manager) {
                    pager.setCurrentItem(2);
                } else {
                    pager.setCurrentItem(3);
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.item_tj);
        notice.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(context, SystemNoticeActivity.class));
            }
        });
        search.setOnSerchListenner(new SearchView.onSerchListenner() {
            @Override
            public void onSerchEvent(String key) {
                if(StringUtils.notEmpty(key)){
                    startActivity(new Intent(context,SearchListActivity.class).putExtra(Constants.Key_1,key));
                }else {
                    MsgUtils.showMDMessage(context,"请输入关键字查询!");
                }
            }
        });
    }

    private void notice() {
        Https.with(this).url(CoreApi.systemNotice).addParam("page", 0).addParam("size", 10).get()
                .enqueue(new Callback<List<SystemNotice>>() {
                    @Override
                    public void success(List<SystemNotice> data) {
                        if (data != null && data.size() > 0) {
                            ArrayList<String> texts = new ArrayList<>();
                            for (SystemNotice entry : data) {
                                texts.add(entry.getNoticeTitle());
                            }
                            notice.setTextList(texts);
                            notice.startAutoScroll();
                        } else {
                            ArrayList<String> texts = new ArrayList<>();
                            texts.add("暂无通知!");
                            notice.setTextList(texts);
                            notice.startAutoScroll();
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showShortToast(context, response.getMsg());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
