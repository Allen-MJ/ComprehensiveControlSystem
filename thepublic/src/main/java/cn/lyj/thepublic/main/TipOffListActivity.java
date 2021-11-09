package cn.lyj.thepublic.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.thepublic.LoginActivity;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.TipOffAdapter;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.SthEntry;

public class TipOffListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private TipOffAdapter adapter;
    private int page = 0,size = 10;
    private boolean isRefresh = false;
    private List<SthEntry> list,sublist;
    private String mKey = "";

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_tipoff_list;
    }

    @Override
    protected void initBar() {
        String title = getIntent().getStringExtra(Constants.Key_1);
        setToolbarTitle(toolbar, StringUtils.empty(title)?"我的爆料":title,true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new TipOffAdapter();
        rv.setAdapter(adapter);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadData();
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 0;
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
                mKey = key;
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                isRefresh = false;
                page = 0;
                loadData();
            }
        });
        adapter.setOnItemClickListener(new TipOffAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View v, SthEntry entry, int position) {
                startActivity(new Intent(context,TipOffInfoActivity.class).putExtra(Constants.Key_1,entry.getAppealId()));
            }
        });
    }

    private void loadData(){
        Https.with(this).url(API._4).addParam("name",mKey)
                .addParam("phone",mKey).addParam("idNumber",mKey).addParam("page",page++).addParam("size",size).get()
                .enqueue(new Callback<List<SthEntry>>() {

                    @Override
                    public void success(List<SthEntry> data) {
                        sublist = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        sublist = new ArrayList<>();
                        showData();
                        MsgUtils.showShortToast(context,"账号登录过期,请重新登录!");
                        startActivityForResult(new Intent(context, LoginActivity.class)
                                .putExtra(Constants.Key_Token,true),11);
                    }

                    @Override
                    public void fail(Response response) {
                        sublist = new ArrayList<>();
                        showData();
                    }
                });
    }

    private void showData() {
        if (isRefresh) {
            list = sublist;
            adapter.setList(list);
            refresh.finishRefresh();
        } else if (page == 1) {
            list = sublist;
            adapter.setList(list);
            refresh.finishLoadMore();
        } else {
            list.addAll(sublist);
            adapter.setList(list);
            refresh.finishLoadMore();
        }
        if(list==null||list.size()==0){
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,"暂无数据");
        }else{
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
        }
        refresh.setNoMoreData(actHelper.isNoMoreData(sublist, size));
    }


}
