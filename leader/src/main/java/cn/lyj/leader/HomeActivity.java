package cn.lyj.leader;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.widget.ContrlScrollViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class HomeActivity extends AllenBaseActivity {
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
        list = new ArrayList<>();
        list.add(ItemFragment.init(0));
        list.add(ItemFragment.init(1));
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        adapter = new FragmentAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(adapter);
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
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.item_tj);
    }

}
