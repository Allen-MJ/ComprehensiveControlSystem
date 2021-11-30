package cn.lyj.user.notice;

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
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.DateUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.user.R;
import cn.lyj.user.R2;
import cn.lyj.user.UserBaseApi;
import cn.lyj.user.entry.SystemNotice;

public class SystemNoticeActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private List<SystemNotice> list,sublist;
    private int page = 0,size = 20;
    private boolean isRefresh = false;
    private CommonAdapter<SystemNotice> adapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.ub_notice_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"系统消息",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new CommonAdapter<SystemNotice>(context,R.layout.ub_notice_item_layout) {
            @Override
            public void convert(ViewHolder holder, SystemNotice entity, int position) {
                holder.setText(R.id.item_title,entity.getNoticeTitle());
                holder.setText(R.id.item_sub,entity.getNoticeSubtitle());
                holder.setText(R.id.item_date, DateUtils.getTimeFormatText(entity.getCreateTime()));
                if("0".equals(entity.getIsRead())){
                    holder.setDrawable(R.id.item_title,R.drawable.lamp_red,0,0,0);
                }else{
                    holder.setDrawable(R.id.item_title,R.drawable.lamp_green,0,0,0);
                }
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
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                startActivity(new Intent(context,SystemNoticeInfoActiivty.class).putExtra(Constants.ObjectFirst,list.get(position)));
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }
    private void loadData(){
        Https.with(this).url(UserBaseApi.SystemNotice)
                .addParam("page",page++).addParam("size",size).get()
                .enqueue(new Callback<List<SystemNotice>>() {

                    @Override
                    public void success(List<SystemNotice> data) {
                        sublist = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        sublist = new ArrayList<>();
                        showData();
                        actHelper.tokenErro2Login(SystemNoticeActivity.this);
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
        if(list==null||list.size()==0){
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,"暂无数据");
        }else{
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
        }
        refresh.setNoMoreData(actHelper.isNoMoreData(sublist, size));
    }
}
