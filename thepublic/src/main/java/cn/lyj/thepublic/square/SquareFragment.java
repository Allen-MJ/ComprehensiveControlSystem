package cn.lyj.thepublic.square;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.widget.ContrlScrollViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.SquareType;

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

    private String[] tabs;
    private List<SquareType> lmList;

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
        fragments = new ArrayList<>();
        loadType();
        addEvent(view);
    }

    private void loadType() {
        Https.with(getActivity())
                .url(API._getType)
                .get()
                .enqueue(new Callback<List<SquareType>>() {
            @Override
            public void success(List<SquareType> data) {
                helper.dismissProgressDialog();

                lmList=data;
                tabs=new String[lmList.size()];
                for (int i = 0; i <lmList.size() ; i++) {
                    tabs[i]=lmList.get(i).getLabel();
                }
                fragments = new ArrayList<>();
                for(int i=0;i<lmList.size();i++){
                    String s=tabs[i];
                    newsTab.addTab(newsTab.newTab().setText(s));
                    fragments.add(SquareNewsFragment.init(lmList.get(i).getValue()));
                }
                adapter = new FragmentAdapter(getChildFragmentManager(), fragments, tabs);
                newsPager.setAdapter(adapter);
                newsTab.setupWithViewPager(newsPager);
            }

            @Override
            public void fail(Response response) {
                helper.dismissProgressDialog();
            }
        });
    }

    private void addEvent(View view) {
    }

}
