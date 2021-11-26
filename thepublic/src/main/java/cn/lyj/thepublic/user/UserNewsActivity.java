package cn.lyj.thepublic.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.SquareMessage;
import cn.lyj.thepublic.entry.UserArt;
import cn.lyj.thepublic.square.NewsDetailActivity;

public class UserNewsActivity extends AllenBaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private static int ZAN_TYPE=0;
    private static int PING_LUN_TYPE=1;
    private static int GUAN_ZHU_TYPE=2;
    private SharedPreferences shared;
    private List<UserArt> list=new ArrayList<>(), sublist;
    private CommonAdapter<UserArt> adapter;
    private boolean isRefresh = false;
    private int page = 0;
    private int pageSize = 20;
    private int type;//
    private String url;
    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_user_news;
    }

    @Override
    protected void initBar() {
        type=getIntent().getIntExtra("type",0);
        if (type==ZAN_TYPE){
            url= API._getZanList;
            setToolbarTitle(toolbar, "我的点赞",true);
        }else if (type==PING_LUN_TYPE){
            url= API._getPinglunList;
            setToolbarTitle(toolbar, "我的评论",true);
        }else if (type==GUAN_ZHU_TYPE){
            url= API._getGuanzhuList;
            setToolbarTitle(toolbar, "我的关注",true);
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        initAdapter();
        refresh.setRefreshHeader(new BezierRadarHeader(context).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(context));
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        loadData();
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter = new CommonAdapter<UserArt>(context, R.layout.item_square_news) {
            @Override
            public void convert(ViewHolder holder, final UserArt entity, int position) {
                holder.setText(R.id.item_source,entity.getServiceInfo().getServiceTitle());
                holder.setText(R.id.item_message, Html.fromHtml(entity.getServiceInfo().getServiceContent()));
                holder.setText(R.id.item_date,entity.getCreateTime());
                holder.setText(R.id.item_zan,entity.getServiceInfo().getLikeCnt()+"");
                if (entity.getServiceInfo().getIsLike()==1){
                    holder.setDrawableLeft(R.id.item_zan,getResources().getDrawable(R.mipmap.square_zan_blue));
                }else {
                    holder.setDrawableLeft(R.id.item_zan,getResources().getDrawable(R.mipmap.square_zan_gray));
                }
                if (entity.getServiceInfo().getIsConcern()==0){
                    holder.setVisible(R.id.item_gz,true);
                    holder.setVisible(R.id.item_dis_gz,false);
                }else {
                    holder.setVisible(R.id.item_gz,false);
                    holder.setVisible(R.id.item_dis_gz,true);
                }
                holder.setOnClickListener(R.id.item_gz, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addGz(entity.getServiceInfo().getServiceId());
                    }
                });
                holder.setOnClickListener(R.id.item_dis_gz, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delGz(entity.getServiceInfo().getServiceId());
                    }
                });
            }
        };
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Logger.e("debug", "onRefresh");
                isRefresh = true;
                page = 0;
                loadData();
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Logger.e("debug", "onLoadMore");
                isRefresh = false;
                loadData();
            }
        });
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                Intent intent=new Intent(context,NewsDetailActivity.class);
                SquareMessage squareMessage=new SquareMessage();
                UserArt.ServiceInfoBean serviceInfoBean=list.get(position).getServiceInfo();
                squareMessage.setCreateTime(serviceInfoBean.getCreateTime());
                squareMessage.setServiceContent(serviceInfoBean.getServiceContent());
                squareMessage.setServiceId(serviceInfoBean.getServiceId());
                squareMessage.setServiceTitle(serviceInfoBean.getServiceTitle());
                squareMessage.setServiceUp(serviceInfoBean.isServiceUp());
                squareMessage.setServiceType(serviceInfoBean.getServiceType());
                squareMessage.setIsConcern(serviceInfoBean.getIsConcern());
                squareMessage.setIsLike(serviceInfoBean.getIsLike());
                squareMessage.setLikeCnt(serviceInfoBean.getLikeCnt());
                intent.putExtra("square",squareMessage);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }


    private void addGz(String id) {
        Https.with(this).url(API._addGuanZhu)
                .addParam("serviceId",id)
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showLongToast(context, "关注成功!");
                        isRefresh = true;
                        page = 0;
                        loadData();
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });
    }

    private void delGz(String id) {
        Https.with(this).url(API._cancelGuanZhu)
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
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });
    }

    private void loadData() {
        Https.with(this).url(url)
                .addParam("page",page++)
                .addParam("size",pageSize)
                .get()
                .enqueue(new Callback<List<UserArt>>() {
                    @Override
                    public void success(List<UserArt> data) {
                        sublist=data;
                        if (isRefresh) {
                            list = sublist;
                            refresh.finishRefresh();
                        } else {
                            if (page == 2) {
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

}