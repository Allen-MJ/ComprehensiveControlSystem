package cn.lyj.thepublic.square;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.SquareMessage;

public class SquareNewsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R2.id.message_type)
    AppCompatTextView messageType;
    @BindView(R2.id.message_search)
    SearchView messageSearch;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.refresh)
    SmartRefreshLayout refresh;
    private ActivityHelper helper;
    private SharedPreferences shared;
    private List<SquareMessage> list=new ArrayList<>(), sublist;
    private CommonAdapter<SquareMessage> adapter;
    private boolean isRefresh = false;
    private int page = 0;
    private int pageSize = 20;
    private String lmID, groupID;
    //    private CommonTypeDialog<GroupEntity> dialog;
    private String type;

    public static SquareNewsFragment init(String type) {
        SquareNewsFragment fragment = new SquareNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key_1, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square_child, container, false);
        helper = new ActivityHelper(getActivity(), view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shared = helper.getSharedPreferences();
        type = getArguments().getString(Constants.Key_1);
        initUI(view);
        addEvent(view);
        loadData();
    }


    private void initUI(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter = new CommonAdapter<SquareMessage>(getContext(), R.layout.item_square_news) {
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
        refresh.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(getActivity()));
        helper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
    }

    private void addEvent(View view) {
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
                Intent intent=new Intent(getContext(),NewsDetailActivity.class);
                intent.putExtra("square",list.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
        helper.setProgressClickListener(new ActivityHelper.OnProgressClickListener() {
            @Override
            public void onAgainClick(View v) {
                isRefresh = true;
                helper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                page = 0;
                loadData();
            }
        });
    }

    /**
     * serviceType：信息类别，便民服务类别字典值value
     * page：当前页码，从0开始， 0为第一页
     * size：每页显示条目数
     */
    private void loadData() {
        Https.with(getActivity()).url(API._getSquare)
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
                            if (page == 2) {
                                list = sublist;
                            } else {
                                list.addAll(sublist);
                            }
                            refresh.finishLoadMore();
                        }
                        adapter.setDatas(list);
                        refresh.setNoMoreData(helper.isNoMoreData(sublist, pageSize));
                        if(list==null||list.size()==0){
                            helper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, "暂无数据");
                        }else{
                            helper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        if(list==null||list.size()==0){
                            helper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, response.getMsg());
                        }else{
                            helper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                        }
                    }
                });

    }

    private void addGz(String id) {
        Https.with(getActivity()).url(API._addGuanZhu)
                .addParam("serviceId",id)
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showLongToast(getContext(), "关注成功!");
                        isRefresh = true;
                        page = 0;
                        loadData();
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(getContext(), response.getMsg());
                    }
                });
    }

    private void delGz(String id) {
        Https.with(getActivity()).url(API._cancelGuanZhu)
                .addParam("serviceId",id)
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showLongToast(getContext(), "已取消关注!");
                        isRefresh = true;
                        page = 0;
                        loadData();
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(getContext(), response.getMsg());
                    }
                });
    }

    @OnClick({R2.id.message_type, R2.id.message_search})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.message_type) {
        } else if (id == R.id.message_search) {
        }
    }
}
