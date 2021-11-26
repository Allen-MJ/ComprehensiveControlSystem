package cn.lyj.core.house;

import android.content.Intent;
import android.os.Bundle;
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
import cn.lyj.core.adapter.HouseAdapter;
import cn.lyj.core.adapter.HousePersonAdapter;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.House;
import cn.lyj.core.entry.HousePerson;
import cn.lyj.core.log.UpdateLogActivity;

/**
 * 房屋管理列表
 */
public class HouseListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private HouseAdapter adapter;
    private int page = 0,size = 10;
    private boolean isRefresh = false;
    private List<House> list,sublist;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        if(menuId==R.id.alen_menu_add){
            startActivityForResult(new Intent(context, UpdateHouseActivity.class),10);
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"实有房屋",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new HouseAdapter();
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
        adapter.setOnItemClickListener(new HouseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, House entry, int position) {
                startActivityForResult(new Intent(context,UpdateHouseActivity.class)
                        .putExtra(Constants.ObjectFirst,entry),10);
            }

            @Override
            public void onItemDelete(View v, House entry, int position) {

            }
        });
    }

    private void loadData(){
        Https.with(this).url(CoreApi.House)
                .addParam("huhao",mKey).addParam("address",mKey).addParam("page",page++).addParam("size",size).get()
                .enqueue(new Callback<List<House>>() {
                    @Override
                    public void success(List<House> data) {
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
