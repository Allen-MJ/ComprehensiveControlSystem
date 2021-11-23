package cn.lyj.core;

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
import allen.frame.entry.User;
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
import cn.lyj.core.adapter.UserChoiceAdapter;
import cn.lyj.core.api.CoreApi;

public class ChoicePersonActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private UserChoiceAdapter adapter;

    private int page = 0, size = 20;
    private boolean isRefresh = false;
    private List<User> list, sublist;
    private String title,ids;
    private boolean single;

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
        ids = getIntent().getStringExtra(Constants.Key_1);
        single = getIntent().getBooleanExtra(Constants.Key_2,false);
        setToolbarTitle(toolbar,"选择人员",true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        if(menuId==R.id.alen_choice_save){
            if(StringUtils.empty(adapter.getIds())){
                MsgUtils.showMDMessage(context,"请选择人员");
            }else{
                Intent intent = getIntent();
                intent.putExtra(Constants.Key_1,adapter.getIds());
                intent.putExtra(Constants.Key_2,adapter.getNames());
                setResult(RESULT_OK,intent);
                finish();
            }
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
        adapter = new UserChoiceAdapter(single);
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
    }

    private void loadData(){
        Https.with(this).url(CoreApi.QueReciviPerson)
                .addParam("uname",title)
                .addParam("page",page++).addParam("size",size).get()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void success(List<User> data) {
                        sublist = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        sublist = new ArrayList<>();
                        showData();
                        MsgUtils.showShortToast(context, "账号登录过期,请重新登录!");
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
            adapter.setList(list,ids);
            refresh.finishRefresh();
        } else if (page == 1) {
            list = sublist;
            adapter.setList(list,ids);
            refresh.finishLoadMore();
        } else {
            list.addAll(sublist);
            adapter.setList(list,ids);
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

}
