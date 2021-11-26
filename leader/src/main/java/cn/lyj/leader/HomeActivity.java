package cn.lyj.leader;

import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.tools.Constants;
import allen.frame.widget.CircleImageView;
import allen.frame.widget.ContrlScrollViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

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
    @BindView(R2.id.pager)
    ContrlScrollViewPager pager;
    @BindView(R2.id.bottom)
    BottomNavigationView bottom;
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
        setToolbarTitle(toolbar, "", false);
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
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        userName.setText(shared.getString(Constants.UserName, ""));
        userGrade.setText(shared.getString(Constants.UserGrage, ""));
        userDw.setText(shared.getString(Constants.UserUnitsName,""));
        adapter = new FragmentAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
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
                }else{
                    pager.setCurrentItem(3);
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.item_tj);
    }

}
