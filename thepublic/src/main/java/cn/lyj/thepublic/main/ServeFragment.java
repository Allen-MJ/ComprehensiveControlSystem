package cn.lyj.thepublic.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import allen.frame.BaseFragment;
import allen.frame.WebActivity;
import allen.frame.tools.Constants;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
import allen.frame.entry.Type;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lyj.thepublic.HomeActivity;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.FyAdapter;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.Notice;
import cn.lyj.thepublic.news.MessageDetailActivity;
import cn.lyj.thepublic.square.LiveListActivity;
import cn.lyjj.thepublic.TipOffListActivity;
import cn.lyjj.thepublic.TipoffActivity;

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
    private List<Notice> notices;
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
        loadData();
    }

    private void initFy() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        rvLifeService.setLayoutManager(manager);
        fyAdapter = new FyAdapter(0);
        List<Type> fys = new ArrayList<>();
        fys.add(new Type("1", "????????????", R.mipmap.main_tianqi));
//        fys.add(new Type("2", "????????????", R.mipmap.main_wuye));
        fys.add(new Type("3", "????????????", R.mipmap.main_zhishiku));
        fyAdapter.setList(fys);
        rvLifeService.setAdapter(fyAdapter);
    }

    private void initNotice() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvNotice.setLayoutManager(manager);
        noticeAdapter=new CommonAdapter<Notice>(getContext(),R.layout.item_main_notice) {
            @Override
            public void convert(ViewHolder holder, Notice entity, int position) {
                holder.setText(R.id.item_source,entity.getNoticeTitle());
                holder.setText(R.id.item_message,entity.getNoticeSubtitle());
                holder.setText(R.id.item_date,entity.getUpdateTime());
                holder.setVisible(R.id.item_zd,entity.isNoticeUp());
            }
        };
        rvNotice.setAdapter(noticeAdapter);
    }


    private void loadData() {
        Https.with(getActivity()).url(API._getNotice)
                .addParam("page",0)
                .addParam("size",10)
                .get()
                .enqueue(new Callback<List<Notice>>() {

                    @Override
                    public void success(List<Notice> data) {
                        notices=data;
                        noticeAdapter.setDatas(notices);
                    }

                    @Override
                    public void fail(Response response) {
                    }
                });

    }
    private void addEvent(View view) {
        fyAdapter.setOnItemClickListener(new FyAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View v, Type type) {
                go2Url(type);
            }
        });
        noticeAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                startActivity(new Intent(getContext(), MessageDetailActivity.class).putExtra("id",notices.get(position).getNoticeId()));
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
        actHelper.setProgressClickListener(new ActivityHelper.OnProgressClickListener() {
            @Override
            public void onAgainClick(View v) {
                loadData();
            }
        });
    }

    private void go2Url(Type type) {
        switch (type.getId()) {
            case "1"://????????????
                startActivity(new Intent(getContext(), WebActivity.class)
                        .putExtra(Constants.Key_1,"????????????")
                        .putExtra(Constants.Key_2,"https://e.weather.com.cn/mweather/101040100.shtml"));
                break;
            case "2"://????????????
                break;
            case "3"://????????????
                startActivity(new Intent(getActivity(), LiveListActivity.class)
                        .putExtra(Constants.Key_1,"3")
                        .putExtra(Constants.Key_2,type.getName()));
                break;
        }
    }

    @OnClick({R2.id.layout_baoliao, R2.id.layout_wenjuan, R2.id.layout_jindu, R2.id.notice_more})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.layout_baoliao) {
            startActivity(new Intent(getActivity(), TipoffActivity.class).putExtra(Constants.Key_1,0));
        } else if (id == R.id.layout_wenjuan) {
            startActivity(new Intent(getContext(),WjdcActivity.class));
        } else if (id == R.id.layout_jindu) {
            startActivity(new Intent(getContext(), TipOffListActivity.class).putExtra(Constants.Key_1,"????????????"));
        } else if (id == R.id.notice_more) {
            ((HomeActivity)getActivity()).goOtherFragment(2);
        }
    }
}
