package cn.lyj.thepublic.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import allen.frame.BaseFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.WebActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Type;
import allen.frame.tools.CheckUtils;
import allen.frame.tools.MsgUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.FyAdapter;
import cn.lyj.thepublic.entry.Notice;

public class ServeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R2.id.main_bg)
    LinearLayoutCompat mainBg;
    @BindView(R2.id.layout_baoliao)
    LinearLayoutCompat layoutBaoliao;
    @BindView(R2.id.layout_wenjuan)
    LinearLayoutCompat layoutWenjuan;
    @BindView(R2.id.layout_jindu)
    LinearLayoutCompat layoutJindu;
    @BindView(R2.id.rv_life_service)
    RecyclerView rvLifeService;
    @BindView(R2.id.notice_more)
    AppCompatTextView noticeMore;
    @BindView(R2.id.rv_notice)
    RecyclerView rvNotice;
    @BindView(R2.id.scroll)
    NestedScrollView scroll;
    private SharedPreferences shared;

    private FyAdapter fyAdapter;
    private CommonAdapter<Notice> noticeAdapter;
//    private ServeSzszAdapter szszAdapter;

    public static ServeFragment init() {
        ServeFragment fragment = new ServeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_serve;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shared = actHelper.getSharedPreferences();
        initUI(view);
        addEvent(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initUI(View view) {
        initFy();
        initNotice();
    }

    private void initFy() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        rvLifeService.setLayoutManager(manager);
        fyAdapter = new FyAdapter(0);
        List<Type> fys = new ArrayList<>();
        fys.add(new Type("1", "天气预报", R.mipmap.main_tianqi));
        fys.add(new Type("2", "物业信息", R.mipmap.main_wuye));
        fys.add(new Type("3", "生活常识", R.mipmap.main_zhishiku));
        fyAdapter.setList(fys);
        rvLifeService.setAdapter(fyAdapter);
    }

    private void initNotice() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvNotice.setLayoutManager(manager);
        noticeAdapter=new CommonAdapter<Notice>(getContext(),R.layout.item_notice) {
            @Override
            public void convert(ViewHolder holder, Notice entity, int position) {

            }
        };
        rvNotice.setAdapter(noticeAdapter);
    }


    private void addEvent(View view) {
        fyAdapter.setOnItemClickListener(new FyAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View v, Type type) {
                go2Url(type);
            }
        });
    }

    private void go2Url(Type type) {
        switch (type.getId()) {
            case "1"://天气预报
                break;
            case "2"://物业管理
                break;
            case "3"://生活常识
                break;
        }
    }

    @OnClick({R2.id.layout_baoliao, R2.id.layout_wenjuan, R2.id.layout_jindu, R2.id.notice_more})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.layout_baoliao) {
        } else if (id == R.id.layout_wenjuan) {
            startActivity(new Intent(getContext(),WjdcActivity.class));
        } else if (id == R.id.layout_jindu) {
        } else if (id == R.id.notice_more) {
        }
    }
}
