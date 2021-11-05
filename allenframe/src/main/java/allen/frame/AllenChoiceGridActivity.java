package allen.frame;

import android.os.Bundle;
import android.view.View;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import allen.frame.adapter.GridChoiceAdapter;
import allen.frame.adapter.UnitsChoiceAdapter;
import allen.frame.entry.Grid;
import allen.frame.entry.Response;
import allen.frame.entry.Units;
import allen.frame.net.BaseApi;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class AllenChoiceGridActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private GridChoiceAdapter adapter;
    private int page = 0,size = 99;
    private boolean isRefresh = false;
    private List<Grid> list,sublist;
    private String mKey="";

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_choice_units_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"网格选择",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new GridChoiceAdapter();
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
        adapter.setOnItemClickListener(new GridChoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Grid grid) {
                setResult(RESULT_OK,getIntent().putExtra(Constants.Key_1,grid.getGid())
                        .putExtra(Constants.Key_2,grid.getGridName()).putExtra(Constants.Key_3,grid.getOrgName()));
                finish();
            }
        });
        search.setOnSerchListenner(new SearchView.onSerchListenner() {
            @Override
            public void onSerchEvent(String key) {
                mKey = key;
                page = 0;
                isRefresh = true;
                loadData();
            }
        });
    }

    private void loadData(){
        Https.with(this).url(BaseApi._2).addParam("page",page++).addParam("size",size)
                .addParam("orgName",mKey).get()
                .enqueue(new Callback<List<Grid>>() {
                    @Override
                    public void success(List<Grid> data) {
                        sublist = data;
                        showData();
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
            adapter.setData(list);
            refresh.finishRefresh();
        } else if (page == 1) {
            list = sublist;
            adapter.setData(list);
            refresh.finishLoadMore();
        } else {
            list.addAll(sublist);
            adapter.setData(list);
            refresh.finishLoadMore();
        }
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
        refresh.setEnableFooterFollowWhenNoMoreData(true);
        refresh.setNoMoreData(actHelper.isNoMoreData(sublist, size));
    }

}
