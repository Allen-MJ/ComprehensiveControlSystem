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
import allen.frame.AllenChoiceUnitsActivity;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
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
import cn.lyj.core.adapter.BmkhAdapter;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.BmkhEntry;

public class BmKhListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.choice_dw)
    AppCompatTextView choiceDw;
    @BindView(R2.id.choice_sfzx)
    AppCompatTextView choiceSfzx;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private BmkhAdapter adapter;
    private String orgid, isspecial;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_kaohe_bm_list;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "部门考核查询", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new BmkhAdapter();
        rv.setAdapter(adapter);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 10:
                    orgid = data.getStringExtra(Constants.Key_1);
                    choiceDw.setText(data.getStringExtra(Constants.Key_2));
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                    loadData();
                    break;
            }
        }
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
    }

    private void loadData() {
        Https.with(this).url(CoreApi.bmkh).addParam("orgid", orgid).addParam("isspecial", isspecial).get().enqueue(new Callback<List<BmkhEntry>>() {

            @Override
            public void success(List<BmkhEntry> data) {
                adapter.setList(data);
                refresh.finishRefresh();
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
            }

            @Override
            public void fail(Response response) {
                adapter.setList(new ArrayList<BmkhEntry>());
                refresh.finishRefresh();
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, response.getMsg());
                MsgUtils.showMDMessage(context, response.getMsg());
            }
        });
    }

    @OnClick({R2.id.choice_dw, R2.id.choice_sfzx})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.choice_dw) {
            startActivityForResult(new Intent(context, AllenChoiceUnitsActivity.class),10);
        } else if (id == R.id.choice_sfzx) {
            final List<Type> list = new ArrayList<>();
            list.add(new Type("0","非专项工作"));
            list.add(new Type("1","专项工作"));
            final CommonTypeDialog<Type> dialog = new CommonTypeDialog<>(context,list);
            dialog.showDialog("请选择是否专项工作", new CommonAdapter<Type>(context, list, R.layout.alen_type_item) {
                @Override
                public void convert(ViewHolder holder, Type entity, int position) {
                    holder.setText(R.id.name_tv,entity.getName());
                }
            }, new CommonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, ViewHolder holder, int position) {
                    dialog.dismiss();
                    isspecial = list.get(position).getId();
                    choiceSfzx.setText(list.get(position).getName());
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
