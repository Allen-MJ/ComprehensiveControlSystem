package cn.lyj.core;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
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
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.SearchEntry;
import cn.lyj.core.person.UpdateXmsfPersonActivity;

/**
 * 统一搜索列表
 */
public class SearchListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private CommonAdapter<SearchEntry> adapter;
    private int page = 0,size = 10;
    private boolean isRefresh = false;
    private List<SearchEntry> list,sublist;
    private String mKey = "";

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_person_list;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"统一搜索",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        mKey = getIntent().getStringExtra(Constants.Key_1);
        search.setText(mKey);
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new CommonAdapter<SearchEntry>(context, R.layout.core_search_item) {
            @Override
            public void convert(ViewHolder holder, final SearchEntry entity, int position) {
                holder.setText(R.id.item_title, Html.fromHtml(entity.getSearchTitle()));
                holder.setText(R.id.item_source, entity.getSource());
                holder.setText(R.id.item_date,entity.getShowTime());
                holder.setText(R.id.item_content,Html.fromHtml(entity.getContent()));
            }
        };
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
                if(StringUtils.empty(key)){
                    MsgUtils.showMDMessage(context,"请输入关键字查询!");
                    return;
                }
                mKey = key;
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                isRefresh = false;
                page = 0;
                loadData();
            }
        });
    }

    private void loadData(){
        Https.with(this).url(CoreApi.search).addParam("page",page++).addParam("size",size)
                .addParam("t",mKey).get()
                .enqueue(new Callback<List<SearchEntry>>() {
                    @Override
                    public void success(List<SearchEntry> data) {
                        sublist = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        sublist = new ArrayList<>();
                        showData();
                        MsgUtils.showShortToast(context,"账号登录过期,请重新登录!");
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
            adapter.setDatas(list);
            refresh.finishRefresh();
        } else if (page == 1) {
            list = sublist;
            adapter.setDatas(list);
            refresh.finishLoadMore();
        } else {
            list.addAll(sublist);
            adapter.setDatas(list);
            refresh.finishLoadMore();
        }
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
        refresh.setEnableFooterFollowWhenNoMoreData(true);
        refresh.setNoMoreData(actHelper.isNoMoreData(sublist, size));
    }
}
