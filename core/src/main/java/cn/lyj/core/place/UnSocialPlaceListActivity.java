package cn.lyj.core.place;

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
import cn.lyj.core.entry.SocialPlaceEntity;
import cn.lyj.core.entry.UnSocialPlaceEntity;

/**
 * 非公有社会场所
 */
public class UnSocialPlaceListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.search)
    SearchView search;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private CommonAdapter<UnSocialPlaceEntity> adapter;
    private int page = 0,size = 10;
    private boolean isRefresh = false;
    private List<UnSocialPlaceEntity> list=new ArrayList<>(),sublist;
    private String mKey = "";
    private String type = "0";
    private List<CoreType> natures;

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
        type = getIntent().getStringExtra(Constants.Key_1);
        setToolbarTitle(toolbar,"非公有制组织",true);
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
            startActivityForResult(new Intent(context, UpdateUnsocialActivity.class),100);
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
        adapter = new CommonAdapter<UnSocialPlaceEntity>(context, R.layout.core_unsocial_person_item) {
            @Override
            public void convert(ViewHolder holder, final UnSocialPlaceEntity entity, int position) {
                holder.setText(R.id.item_name,entity.getB2302());
                if (natures!=null&&natures.size()>0){
                    for (CoreType type:natures
                    ) {
                        if (type.getValue().equals(entity.getB2303())){
                            holder.setText(R.id.item_nature,type.getLabel());
                        }
                    }
                }

                holder.setText(R.id.item_phone,entity.getB2305());

                holder.setOnClickListener(R.id.item_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MsgUtils.showMDMessage(context, "确定删除吗？", "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id=entity.getB2300();
                                String[] ids=new String[]{id};
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
        };
        rv.setAdapter(adapter);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadNature();
        loadData();
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
                Intent intent=new Intent(context,UpdateUnsocialActivity.class);
                intent.putExtra(Constants.ObjectFirst,list.get(position));
                startActivityForResult(intent,100);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }
    private void delete(String[] ids){
        Https.with(this).url(CoreApi._core_13)
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
                        MsgUtils.showShortToast(context,"账号登录过期,请重新登录!");
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
    private void loadData(){
        Https.with(this).url(CoreApi._core_13).addParam("page",page++).addParam("size",size)
                .addParam("b2302",mKey).get()
                .enqueue(new Callback<List<UnSocialPlaceEntity>>() {
                    @Override
                    public void success(List<UnSocialPlaceEntity> data) {
                        sublist = data;
                        showData();
                    }

                    @Override
                    public void token() {
                        sublist = new ArrayList<>();
                        showData();
                        actHelper.tokenErro2Login(UnSocialPlaceListActivity.this);
                    }

                    @Override
                    public void fail(Response response) {
                        sublist = new ArrayList<>();
                        showData();
                    }
                });
    }

    private void loadNature(){
        showProgressDialog("");
        Https.with(this).url(CoreApi.core_Type).addParam("dictName","business_type").addParam("page",0).addParam("size",9999).get()
                .enqueue(new Callback<List<CoreType>>() {

                    @Override
                    public void success(final List<CoreType> data) {
                        dismissProgressDialog();
                        natures=data;
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
