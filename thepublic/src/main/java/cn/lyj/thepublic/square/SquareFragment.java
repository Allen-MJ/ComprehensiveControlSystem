package cn.lyj.thepublic.square;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.widget.ContrlScrollViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.news.MessageFragment;

public class SquareFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R2.id.news_tab)
    TabLayout newsTab;
    @BindView(R2.id.news_pager)
    ContrlScrollViewPager newsPager;
    private ActivityHelper helper;
    private SharedPreferences shared;
    private FragmentAdapter adapter;
    private List<Fragment> fragments;

    private String[] tabs=new String[]{"关注","推荐"};

    public static SquareFragment init() {
        SquareFragment fragment = new SquareFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        helper = new ActivityHelper(getActivity(), view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shared = helper.getSharedPreferences();
        setTab();
        adapter = new FragmentAdapter(getChildFragmentManager(), fragments, tabs);
        newsPager.setAdapter(adapter);
        newsTab.setupWithViewPager(newsPager);
        addEvent(view);
    }

    private void setTab(){
        for(int i=0;i<tabs.length;i++){
            newsTab.addTab(newsTab.newTab().setText(tabs[i]));
//            newsTab.getTabAt(i).setText(tabs[i]);
            fragments.add(SquareNewsFragment.init(i));
        }
    }


    private void addEvent(View view) {
    }

}
