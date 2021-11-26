package cn.lyj.thepublic.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import allen.frame.tools.Logger;
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
import cn.lyj.thepublic.entry.Notice;

public class MessageFragment extends Fragment {

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
    private List<Notice> list=new ArrayList<>(), sublist;
    private CommonAdapter<Notice> adapter;
    private boolean isRefresh = false;
    private int page = 0;
    private int pageSize = 20;
    private String lmID, groupID;

    public static MessageFragment init() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
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
        initUI(view);
        addEvent(view);
        loadData();
    }


    private void initUI(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter = new CommonAdapter<Notice>(getContext(),R.layout.item_notice) {
            @Override
            public void convert(ViewHolder holder, Notice entity, int position) {
                holder.setText(R.id.item_source,entity.getNoticeTitle());
                holder.setText(R.id.item_message,entity.getNoticeSubtitle());
                holder.setText(R.id.item_date,entity.getUpdateTime());
            }
        };
        recyclerview.setAdapter(adapter);
        refresh.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        refresh.setRefreshFooter(new ClassicsFooter(getActivity()));

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
                startActivity(new Intent(getContext(),MessageDetailActivity.class).putExtra("id",list.get(position).getNoticeId()));
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

    private void loadData() {
        Https.with(getActivity()).url(API._getNotice)
                .addParam("page",page++)
                .addParam("size",pageSize)
                .get()
                .enqueue(new Callback<List<Notice>>() {

            @Override
            public void success(List<Notice> data) {
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

    @OnClick({R2.id.message_type, R2.id.message_search})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.message_type) {
        } else if (id == R.id.message_search) {
        }
    }
}
