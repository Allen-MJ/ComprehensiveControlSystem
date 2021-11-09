package cn.lyj.thepublic.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.WjdcAdapter;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.WjdcEntity;

public class WjdcActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar bar;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.layout_search)
    LinearLayoutCompat layoutSearch;

    private ActivityHelper helper;
    private SharedPreferences shared;
    private WjdcAdapter adapter;
    private List<WjdcEntity> sublist;
    private List<WjdcEntity> list;
    private boolean isRefresh=false;
    private int uid, gid;
    private int page = 0, pageSize = 20;

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
        setToolbarTitle(bar, "问卷调查",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        layoutSearch.setVisibility(View.VISIBLE);
        shared = actHelper.getSharedPreferences();
        uid=shared.getInt(Constants.UserId,0);
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
        Https.with(this).url(API._getWjdcList).get().enqueue(new Callback<List<WjdcEntity>>() {

            @Override
            public void success(List<WjdcEntity> data) {
                list=data;
                adapter.setList(list);
                if(list==null||list.size()==0){
                    helper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, "暂无数据");
                }else{
                    helper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                }
            }

            @Override
            public void fail(Response response) {
                if(list==null||list.size()==0){
                    helper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, response.getMsg());
                }else{
                    helper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if (resultCode==RESULT_OK){
                    loadData();
                }
                break;
        }
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
            public void itemClick(View v, WjdcEntity wjListBean) {
                Intent intent = new Intent(context, VoteActivity.class);
                intent.putExtra("Wjdc",wjListBean);
                startActivityForResult(intent,100);
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page=0;
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
                page=0;
                loadData();
            }
        });
    }

    private void loadShow(){
        if(isRefresh){
            list = sublist;
            adapter.setList(list);
            refresh.finishRefresh();
        }else if(page==1){
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
