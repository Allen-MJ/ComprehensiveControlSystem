package cn.lyj.core.house;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
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
import allen.frame.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.adapter.RentHouseAdapter;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.RentHouse;
import cn.lyj.core.entry.SocialPlaceEntity;

/**
 * 房屋管理列表
 */
public class RentHouseListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private RentHouseAdapter adapter;
    private int page = 0,size = 10;
    private boolean isRefresh = false;
    private List<RentHouse> list,sublist;
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
        setToolbarTitle(toolbar,"出租房",true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        if(menuId== R.id.alen_menu_add){
            startActivityForResult(new Intent(context, UpdateRentHouseActivity.class),10);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new RentHouseAdapter();
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
        adapter.setOnItemClickListener(new RentHouseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, RentHouse entry, int position) {
                startActivityForResult(new Intent(context, UpdateRentHouseActivity.class)
                        .putExtra(Constants.ObjectFirst,entry),10);
            }

            @Override
            public void onItemDelete(View v, final RentHouse entry, int position) {
                MsgUtils.showMDMessage(context, "确定删除吗？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] ids = new String[]{entry.getB1600()};
                        delete(ids);
                        dialog.dismiss();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void loadData(){
        Https.with(this).url(CoreApi.RentHouse)
                .addParam("b1202",mKey).addParam("page",page++).addParam("size",size).get()
                .enqueue(new Callback<List<RentHouse>>() {
                    @Override
                    public void success(List<RentHouse> data) {
                        sublist = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        sublist = new ArrayList<>();
                        showData();
                        actHelper.tokenErro2Login(RentHouseListActivity.this);
                    }

                    @Override
                    public void fail(Response response) {
                        sublist = new ArrayList<>();
                        showData();
                    }
                });
    }

    private void delete(String[] ids){
        Https.with(this).url(CoreApi.RentHouseDelete)
                .addJsons(new Gson().toJson(ids)).delete()
                .enqueue(new Callback<List<SocialPlaceEntity>>() {
                    @Override
                    public void success(List<SocialPlaceEntity> data) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,"删除成功！");
                        isRefresh = true;
                        page = 0;
                        loadData();
                    }

                    @Override
                    public void token() {
                        dismissProgressDialog();
                        actHelper.tokenErro2Login(RentHouseListActivity.this);
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
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
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
        refresh.setEnableFooterFollowWhenNoMoreData(true);
        refresh.setNoMoreData(actHelper.isNoMoreData(sublist, size));
    }
}
