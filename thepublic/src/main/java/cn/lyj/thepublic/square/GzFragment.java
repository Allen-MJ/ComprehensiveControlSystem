package cn.lyj.thepublic.square;

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
import allen.frame.BaseFragment;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Logger;
import allen.frame.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.SquareMessage;
import cn.lyj.thepublic.entry.UserArt;

public class GzFragment extends BaseFragment {
    @BindView(R2.id.message_type)
    AppCompatTextView messageType;
    @BindView(R2.id.message_search)
    SearchView messageSearch;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private List<UserArt> list=new ArrayList<>(), sublist;
    private CommonAdapter<UserArt> adapter;
    private boolean isRefresh = false;
    private int page = 0;
    private int pageSize = 20;

    public static GzFragment init(){
        GzFragment fragment = new GzFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_square_child;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        refresh.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(getActivity()));
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        loadData();
    }
    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter = new CommonAdapter<UserArt>(getActivity(), R.layout.item_square_news) {
            @Override
            public void convert(ViewHolder holder, UserArt entity, int position) {
                holder.setText(R.id.item_source,entity.getServiceInfo().getServiceTitle());
                holder.setText(R.id.item_message, Html.fromHtml(entity.getServiceInfo().getServiceContent()));
                holder.setText(R.id.item_date,entity.getCreateTime());
                holder.setText(R.id.item_zan,entity.getServiceInfo().getLikeCnt()+"");
                if (entity.getServiceInfo().getIsLike()==1){
                    holder.setDrawableLeft(R.id.item_zan,getResources().getDrawable(R.mipmap.square_zan_blue));
                }else {
                    holder.setDrawableLeft(R.id.item_zan,getResources().getDrawable(R.mipmap.square_zan_gray));
                }
            }
        };
        recyclerview.setAdapter(adapter);
    }

    private void addEvent() {
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
                Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
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
    private void loadData() {
        Https.with(getActivity()).url(API._getGuanzhuList)
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
