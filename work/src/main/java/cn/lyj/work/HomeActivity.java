package cn.lyj.work;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    AppCompatTextView xlBt;
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
                .error(R.drawable.default_photo)
                .into(userPhoto);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        userName.setText(shared.getString(Constants.UserName, ""));
        userGrade.setText(shared.getString(Constants.UserGrage, ""));
        list = new ArrayList<>();
        list.add(ModelFragment.init(0));
        list.add(ModelFragment.init(1));
        list.add(ModelFragment.init(2));
        pager.setAdapter(adapter);
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
    }

    @OnClick({R2.id.user_photo, R2.id.xl_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        view.setEnabled(true);
    }

}
