package cn.lyj.thepublic.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.tools.CommonTypeDialog;
import allen.frame.tools.Logger;
import allen.frame.widget.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lyj.thepublic.R;

public class MessageFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rdwt_type)
    AppCompatTextView rdwtType;
    @BindView(R.id.rdwt_search)
    SearchView rdwtSearch;
    private ActivityHelper helper;
    private SharedPreferences shared;
    private List<MessageEntity> list, sublist;
    private boolean isRefresh = false;
    private int page = 1;
    private int pageSize = 20;
    private String lmID, groupID;
//    private CommonTypeDialog<GroupEntity> dialog;
//    private MessageLmEntity messageLmEntity;

    public static MessageFragment init() {
        MessageFragment fragment = new MessageFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Constants.Key_1, lmEntity);
//        fragment.setArguments(bundle);
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
//        messageLmEntity= (MessageLmEntity) getArguments().getSerializable(Constants.Key_1);
        initUI(view);
        addEvent(view);
//        loadGroup();
        loadData();
    }

    private void loadGroup() {
        helper.showProgressDialog("");

    }

    private void initUI(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
//        adapter = new RdwtAdapter();
//        rv.setAdapter(adapter);
//        refresh.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
//        refresh.setRefreshFooter(new ClassicsFooter(getActivity()));
//        helper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
    }

    private void addEvent(View view) {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Logger.e("debug", "onRefresh");
                isRefresh = true;
                page = 1;
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
    }

    private void loadData() {

    }

    @OnClick({R.id.rdwt_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rdwt_type:
                loadGroup();
                break;
        }
    }
}
