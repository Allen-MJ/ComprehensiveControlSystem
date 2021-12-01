package cn.lyj.core.word;

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
import allen.frame.tools.CommonTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.adapter.WordAdapter;
import cn.lyj.core.api.CoreApi;
import allen.frame.entry.CoreType;
import cn.lyj.core.entry.Word;

public class ReceivListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.choice_status)
    AppCompatTextView choiceStatus;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;

    private int page = 0, size = 20;
    private boolean isRefresh = false;
    private List<Word> list, sublist;
    private String title,state;
    private WordAdapter adapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_word_list;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"我的收文",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new WordAdapter(false);
        rv.setAdapter(adapter);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadData();
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                finish();
                v.setEnabled(true);
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
                title = key;
                isRefresh = true;
                page = 0;
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                loadData();
            }
        });
        adapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Word entry, int position) {
                startActivityForResult(new Intent(context, ReceivWordInfoActivty.class)
                        .putExtra(Constants.ObjectFirst,entry),10);
            }

            @Override
            public void onDelete(View v, final Word entry, int position) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==10){
                isRefresh = true;
                page = 0;
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                loadData();
            }
        }
    }

    private void loadData() {
        Https.with(this).url(CoreApi.Missive)
                .addParam("title",title).addParam("state",state).addParam("isMySigner",false)
                .addParam("page",page++).addParam("size",size).get()
                .enqueue(new Callback<List<Word>>() {
                    @Override
                    public void success(List<Word> data) {
                        sublist = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        sublist = new ArrayList<>();
                        showData();
                        actHelper.tokenErro2Login(ReceivListActivity.this);
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
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, "暂无数据!");
        }else{
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
        }
        refresh.setEnableFooterFollowWhenNoMoreData(true);
        refresh.setNoMoreData(actHelper.isNoMoreData(sublist, size));
    }

    @OnClick(R2.id.choice_status)
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id== R.id.choice_status){
            status();
        }
        view.setEnabled(true);
    }

    private void status(){
        showProgressDialog("");
        Https.with(this).url(CoreApi.core_Type).addParam("dictName","receive_state").addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {

                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                        dialog.showDialog("请选择签收状态", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                            @Override
                            public void convert(ViewHolder holder, CoreType entity, int position) {
                                holder.setText(R.id.name_tv,entity.getLabel());
                            }
                        }, new CommonAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, ViewHolder holder, int position) {
                                dialog.dismiss();
                                state = data.get(position).getValue();
                                choiceStatus.setText(data.get(position).getLabel());
                                page = 0;
                                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                                isRefresh = true;
                                loadData();
                            }

                            @Override
                            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                                return false;
                            }
                        }).show();
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }

}
