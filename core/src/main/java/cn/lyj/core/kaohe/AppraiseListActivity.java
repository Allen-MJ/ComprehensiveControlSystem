package cn.lyj.core.kaohe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.CoreType;
import allen.frame.entry.Response;
import allen.frame.entry.Type;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.CommonTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
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
import cn.lyj.core.adapter.KaoheAdapter;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.KaoheEntry;

public class AppraiseListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.choice_type)
    AppCompatTextView choiceType;
    @BindView(R2.id.choice_status)
    AppCompatTextView choiceStatus;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private int examineState = 0;
    private String examineType;
    private KaoheAdapter adapter;
    private List<CoreType> types;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_kaohe_appraise_list;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"我的考核",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        choiceStatus.setText("未完成");
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new KaoheAdapter();
        rv.setAdapter(adapter);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                loadData();
            }
        });
        adapter.setOnItemClickListener(new KaoheAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, KaoheEntry entry, int position) {
                startActivity(new Intent(context,AppraiseInfoActivity.class).putExtra(Constants.ObjectFirst,entry));
            }
        });
    }

    private void loadData() {
        Https.with(this).url(CoreApi.mykh).addParam("examineType", examineType).addParam("examineState", examineState).get()
                .enqueue(new Callback<List<KaoheEntry>>() {

            @Override
            public void success(List<KaoheEntry> data) {
                adapter.setList(data);
                refresh.finishRefresh();
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
            }

            @Override
            public void fail(Response response) {
                adapter.setList(new ArrayList<KaoheEntry>());
                refresh.finishRefresh();
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, response.getMsg());
                MsgUtils.showMDMessage(context, response.getMsg());
            }
        });
    }

    @OnClick({R2.id.choice_type, R2.id.choice_status})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.choice_type) {
            loadType();
        } else if (id == R.id.choice_status) {
            final List<Type> list = new ArrayList<>();
            list.add(new Type("0","未完成"));
            list.add(new Type("1","已完成"));
            final CommonTypeDialog<Type> dialog = new CommonTypeDialog<>(context,list);
            dialog.showDialog("请选择考核状态", new CommonAdapter<Type>(context, list, R.layout.alen_type_item) {
                @Override
                public void convert(ViewHolder holder, Type entity, int position) {
                    holder.setText(R.id.name_tv,entity.getName());
                }
            }, new CommonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, ViewHolder holder, int position) {
                    dialog.dismiss();
                    examineState = Integer.parseInt(list.get(position).getId());
                    choiceStatus.setText(list.get(position).getName());
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                    loadData();
                }

                @Override
                public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                    return false;
                }
            }).show();
        }
    }

    private void loadType(){
        showProgressDialog("");
        int len = types==null?0:types.size();
        if(len==0){
            Https.with(this).url(CoreApi.core_Type).addParam("dictName","grid_examine_type").addParam("page",0).addParam("size",9999).get()
                    .enqueue(new Callback<List<CoreType>>() {

                        @Override
                        public void success(final List<CoreType> data) {
                            types = data;
                            dismissProgressDialog();
                            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,data);
                            dialog.showDialog("请选择考核类型", new CommonAdapter<CoreType>(context, data, R.layout.alen_type_item) {
                                @Override
                                public void convert(ViewHolder holder, CoreType entity, int position) {
                                    holder.setText(R.id.name_tv,entity.getLabel());
                                }
                            }, new CommonAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, ViewHolder holder, int position) {
                                    dialog.dismiss();
                                    examineType = data.get(position).getValue();
                                    choiceType.setText(data.get(position).getLabel());
                                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
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
        }else{
            dismissProgressDialog();
            final CommonTypeDialog<CoreType> dialog = new CommonTypeDialog<>(context,types);
            dialog.showDialog("请选择考核类型", new CommonAdapter<CoreType>(context, types, R.layout.alen_type_item) {
                @Override
                public void convert(ViewHolder holder, CoreType entity, int position) {
                    holder.setText(R.id.name_tv,entity.getLabel());
                }
            }, new CommonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, ViewHolder holder, int position) {
                    dialog.dismiss();
                    examineType = types.get(position).getValue();
                    choiceType.setText(types.get(position).getLabel());
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                    loadData();
                }

                @Override
                public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                    return false;
                }
            }).show();
        }
    }
}
