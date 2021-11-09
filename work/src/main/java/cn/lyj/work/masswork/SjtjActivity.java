package cn.lyj.work.masswork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Http;
import allen.frame.tools.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.work.R;
import cn.lyj.work.R2;
import cn.lyj.work.entry.SxtjEntity;
import cn.lyj.work.utils.OnNumDataListenner;

public class SjtjActivity extends AllenBaseActivity {


    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.key_et)
    AppCompatEditText keyEt;
    @BindView(R2.id.key_bt)
    AppCompatImageView keyBt;
    @BindView(R2.id.list)
    RecyclerView recyclerView;
    @BindView(R2.id.mater)
    SmartRefreshLayout mater;

    private List<SxtjEntity> sublist, list;
    private int page = 0, pagesize = 20;
    private String ucode, uType;
    private String key = "";
    private boolean isRefresh = false;
    private CommonAdapter<SxtjEntity> adapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                    if (isRefresh) {
                        list = sublist;
                        mater.finishRefresh();
                    } else {
                        if (page == 1) {
                            list = sublist;
                        } else {
                            list.addAll(sublist);
                        }
                        mater.finishLoadMore();
                    }
                    if (list.size() == 0) {
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, "");
                    }
                    adapter.setDatas(list);
                    mater.setNoMoreData(actHelper.isNoMoreData(sublist, pagesize));
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        actHelper.hideSoftInputView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        actHelper.hideSoftInputView();
    }

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.sth_list_layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                isRefresh = true;
                page = 0;
                loadData();
            }
        }
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
//        ucode = actHelper.getSharedPreferences().getString(Constants.USER_UNITCODE, "");
//        uType = actHelper.getSharedPreferences().getString(Constants.USER_UNITTYPE, "");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        key = getIntent().getStringExtra("Text");
        initAdapter();
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        loadData();
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new CommonAdapter<SxtjEntity>(context, R.layout.sth_db_item_layout) {
            @Override
            public void convert(ViewHolder holder, SxtjEntity entry, int position) {
                TextView fysx = holder.getView(R.id.db_item_sx);
                holder.setText(R.id.db_item_sx, entry.getRemark());
                String light = entry.getLight();
                if (light.equals("1")) {
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_green, 0, 0, 0);
                } else if (light.equals("2")) {
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_yelloy, 0, 0, 0);
                } else if (light.equals("3")) {
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_red, 0, 0, 0);
                } else {
                    fysx.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lamp_blue, 0, 0, 0);
                }
                holder.setText(R.id.db_item_sldw, "事项来源:" + entry.getAddress());
                holder.setText(R.id.db_item_jjcd, "反映人：" + entry.getName());
                holder.setText(R.id.db_item_fyr, "办理单位:" + entry.getUName());
                holder.setText(R.id.db_item_slsj, "事项内容:" + entry.getRemark());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mater.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 0;
                loadData();
            }
        });
        adapter.setOnItemClickListener(itemclick);
    }

    private CommonAdapter.OnItemClickListener itemclick = new CommonAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, ViewHolder holder, int position) {
            startActivityForResult(new Intent(context, SthDbDetailActivity.class)
                    .putExtra(Constants.STH_ID, list.get(position).getId()), 1);
        }

        @Override
        public boolean onItemLongClick(View view, ViewHolder holder, int position) {
            return false;
        }
    };

    private OnNumDataListenner dataListenner = new OnNumDataListenner() {
        @Override
        public void showTotal(final String num) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toolbar.setTitle("(" + num + ")事项统计");
                }
            });
        }

        @Override
        public void enpty() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toolbar.setTitle("事项统计");
                }
            });
        }
    };

    private void loadData() {
        Http.with(this).url("").parameters(new Object[]{"", ""}).enqueue(new Callback<List<SxtjEntity>>() {
            @Override
            public void success(List<SxtjEntity> data) {
                sublist=data;
                handler.sendEmptyMessage(0);
            }

            @Override
            public void fail(Response response) {

            }
        }).post();
    }


    @OnClick(R2.id.key_bt)
    public void onViewClicked() {
        key = keyEt.getText().toString().trim();
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        isRefresh = true;
        page = 0;
        loadData();
    }
}
