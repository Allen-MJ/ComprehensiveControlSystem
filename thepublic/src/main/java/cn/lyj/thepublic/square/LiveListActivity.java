package cn.lyj.thepublic.square;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

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
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.SquareMessage;

public class LiveListActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private String type,title;

    private List<SquareMessage> list=new ArrayList<>(), sublist;
    private CommonAdapter<SquareMessage> adapter;
    private boolean isRefresh = false;
    private int page = 0;
    private int pageSize = 20;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_activity_xxdt;
    }

    @Override
    protected void initBar() {
        type = getIntent().getStringExtra(Constants.Key_1);
        title = getIntent().getStringExtra(Constants.Key_2);
        setToolbarTitle(toolbar,title,true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        initAdapter();
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
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
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                Intent intent=new Intent(context,NewsDetailActivity.class);
                intent.putExtra("square",list.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter = new CommonAdapter<SquareMessage>(context, R.layout.item_square_news) {
            @Override
            public void convert(ViewHolder holder, final SquareMessage entity, int position) {
                holder.setText(R.id.item_source,entity.getServiceTitle());
                holder.setText(R.id.item_message, Html.fromHtml(entity.getServiceContent()));
                holder.setText(R.id.item_date,entity.getCreateTime());
                holder.setText(R.id.item_zan,entity.getLikeCnt()+"");
                if (entity.getIsLike()==1){
                    holder.setDrawableLeft(R.id.item_zan,getResources().getDrawable(R.mipmap.square_zan_blue));
                }else {
                    holder.setDrawableLeft(R.id.item_zan,getResources().getDrawable(R.mipmap.square_zan_gray));
                }
                if (entity.getIsConcern()==0){
                    holder.setVisible(R.id.item_gz,true);
                    holder.setVisible(R.id.item_dis_gz,false);
                }else {
                    holder.setVisible(R.id.item_gz,false);
                    holder.setVisible(R.id.item_dis_gz,true);
                }
                holder.setOnClickListener(R.id.item_gz, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addGz(entity.getServiceId());
                    }
                });
                holder.setOnClickListener(R.id.item_dis_gz, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delGz(entity.getServiceId());
                    }
                });

            }
        };
        recyclerview.setAdapter(adapter);
    }

    private void loadData() {
        Https.with(context).url(API._getSquare)
                .addParam("serviceType",type)
                .addParam("page",page++)
                .addParam("size",pageSize)
                .get()
                .enqueue(new Callback<List<SquareMessage>>() {
                    @Override
                    public void success(List<SquareMessage> data) {
                        sublist=data;
                        if (isRefresh) {
                            list = sublist;
                            refresh.finishRefresh();
                        } else {
                            if (page == 1) {
                                list = sublist;
                            } else {
                                list.addAll(sublist);
                            }
                            refresh.finishLoadMore();
                        }
                        adapter.setDatas(list);
                        refresh.setNoMoreData(actHelper.isNoMoreData(sublist, pageSize));
                        if(list==null||list.size()==0){
                            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, "暂无数据");
                        }else{
                            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        if(list==null||list.size()==0){
                            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, response.getMsg());
                        }else{
                            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                        }
                    }
                });
    }

    private void addGz(String id) {
        Https.with(context).url(API._addGuanZhu)
                .addParam("serviceId",id)
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showShortToast(context, "关注成功!");
                        isRefresh = true;
                        page = 0;
                        loadData();
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void delGz(String id) {
        Https.with(context).url(API._cancelGuanZhu)
                .addParam("serviceId",id)
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showLongToast(context, "已取消关注!");
                        isRefresh = true;
                        page = 0;
                        loadData();
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

}
