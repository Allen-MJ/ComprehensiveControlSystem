package cn.lyj.thepublic;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenIMBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.widget.ContrlScrollViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import cn.lyj.thepublic.main.ServeFragment;
import cn.lyj.thepublic.news.MessageFragment;
import cn.lyj.thepublic.square.SquareFragment;
import cn.lyj.thepublic.user.OwnFragment;

public class HomeActivity extends AllenIMBaseActivity {
    @BindView(R2.id.pager)
    ContrlScrollViewPager pager;
    @BindView(R2.id.bottom)
    BottomNavigationView bottom;
    private FragmentAdapter adapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_home_layout;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        List<Fragment> list = new ArrayList<>();
        list.add(ServeFragment.init());
//        list.add(SquareFragment.init());
        list.add(MessageFragment.init());
        list.add(MessageFragment.init());
        list.add(OwnFragment.init());
        adapter = new FragmentAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(adapter);
    }

    @Override
    protected void addEvent() {
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.item_serve){
                    pager.setCurrentItem(0);
                }else if(id==R.id.item_serve){
                    pager.setCurrentItem(1);
                }else if(id==R.id.item_serve){
                    pager.setCurrentItem(2);
                }else{
                    pager.setCurrentItem(3);
                }
                return true;
            }
        });
        bottom.setSelectedItemId(R.id.item_serve);
    }

}
