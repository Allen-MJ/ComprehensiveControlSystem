package cn.lyj.thepublic.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.widget.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lyj.thepublic.adapter.WjdcAdapter;

public class WjdcActivity extends AllenBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.layout_search)
    LinearLayoutCompat layoutSearch;

    private ActivityHelper helper;
    private SharedPreferences shared;
    private WjdcAdapter adapter;
    private List<YsjdEntity.WjListBean> sublist;
    private List<YsjdEntity.WjListBean> list;
    private boolean isRefresh=false;
    private int uid, gid;
    private int page = 1, pageSize = 20;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_xxdt;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        setToolbarTitle(bar, getTitle());
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        layoutSearch.setVisibility(View.VISIBLE);
        shared = actHelper.getSharedPreferences();
        uid=shared.getInt(Constants.UserId,0);
        gid=shared.getInt(Constants.UserRelateGridId,0);
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        initAdapter();
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        loadData();
    }


    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter = new WjdcAdapter();
        recyclerview.setAdapter(adapter);
    }

    private void loadData() {
        WebHelper.init().getWjdc(uid, gid, search.getText().toString(),page++, pageSize, new HttpCallBack<List<YsjdEntity.WjListBean>>() {
            @Override
            public void onSuccess(List<YsjdEntity.WjListBean> respone) {
                sublist = respone;
                loadShow();
            }

            @Override
            public void onTodo(Respone respone) {
            }

            @Override
            public void tokenErro(Respone respone) {
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, respone.getMessage());
            }

            @Override
            public void onFailed(Respone respone) {
                sublist = new ArrayList<>();
                loadShow();
            }
        });
    }


    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter.setOnItemClickListener(new WjdcAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View v, YsjdEntity.WjListBean wjListBean) {
                Intent intent = new Intent(context, VoteActivity.class);
                intent.putExtra("Wjdc",wjListBean);
                startActivity(intent);
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page=1;
                loadData();
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                loadData();
            }
        });
        search.setOnSerchListenner(new SearchView.onSerchListenner() {
            @Override
            public void onSerchEvent(String key) {
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
                isRefresh = true;
                page=1;
                loadData();
            }
        });
    }

    private void loadShow(){
        if(isRefresh){
            list = sublist;
            adapter.setList(list);
            refresh.finishRefresh();
        }else if(page==2){
            list = sublist;
            adapter.setList(list);
            refresh.finishLoadMore();
        }else{
            list.addAll(sublist);
            adapter.setList(list);
            refresh.finishLoadMore();
        }
        refresh.setNoMoreData(actHelper.isNoMoreData(sublist,pageSize));
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
    }
}
