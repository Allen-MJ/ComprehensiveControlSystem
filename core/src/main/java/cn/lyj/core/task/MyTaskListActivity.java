package cn.lyj.core.task;

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
import allen.frame.tools.MsgUtils;
import allen.frame.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.api.CoreApi;
import allen.frame.entry.CoreType;
import cn.lyj.core.entry.TaskEntity;

/**
 * 我的任务列表
 */
public class MyTaskListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private CommonAdapter<TaskEntity> adapter;
    private int page = 0,size = 10;
    private boolean isRefresh = false;
    private List<TaskEntity> list,sublist;
    private List<CoreType> coreTypes;
    private String mKey = "";
    private String type = "0";

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_task_list;
    }

    @Override
    protected void initBar() {
        type = getIntent().getStringExtra(Constants.Key_1);
        setToolbarTitle(toolbar,"我的任务",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        initAdapter();
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadProgress();
        loadData();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<TaskEntity>(context, R.layout.task_item) {
            @Override
            public void convert(ViewHolder holder, TaskEntity entity, int position) {
                holder.setText(R.id.item_name,entity.getTaskName());
                holder.setText(R.id.item_info,entity.getTaskDesc());
                holder.setText(R.id.item_date,entity.getCreateTime());
                holder.setText(R.id.item_user,entity.getCreateBy());
                if (coreTypes!=null&&coreTypes.size()>0){
                    for (CoreType type:coreTypes
                         ) {
                        if (type.getValue().equals(entity.getProgress()+"")){
                            holder.setText(R.id.item_state,type.getLabel());
                        }
                    }
                }



            }
        };
        rv.setAdapter(adapter);
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
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                startActivityForResult(new Intent(context, TaskDeailActivity.class).putExtra(Constants.Key_1,list.get(position)),100);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    isRefresh = true;
                    page = 0;
                    loadData();
                    break;
            }
        }
    }

    private void loadProgress(){
        showProgressDialog("");
        Https.with(this).url(CoreApi.core_Type).addParam("dictName","task_progress").addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {

                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        coreTypes=data;
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
    private void loadData(){
        Https.with(this).url(CoreApi._get_task_list)
                .addParam("page",page++)
                .addParam("size",size)
                .addParam("sort","createTime,desc")
                .get()
                .enqueue(new Callback<List<TaskEntity>>() {
                    @Override
                    public void success(List<TaskEntity> data) {
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
