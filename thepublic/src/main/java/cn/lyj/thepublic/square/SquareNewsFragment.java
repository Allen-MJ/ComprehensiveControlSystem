package cn.lyj.thepublic.square;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import allen.frame.tools.Constants;
import allen.frame.tools.Logger;
import allen.frame.widget.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.entry.MessageEntity;

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
    private List<MessageEntity> list, sublist;
    private CommonAdapter<MessageEntity> adapter;
    private boolean isRefresh = false;
    private int page = 1;
    private int pageSize = 20;
    private String lmID, groupID;
//    private CommonTypeDialog<GroupEntity> dialog;
    private int  type;

    public static SquareNewsFragment init(int type) {
        SquareNewsFragment fragment = new SquareNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key_1, type);
        fragment.setArguments(bundle);
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
        type=  getArguments().getInt(Constants.Key_1);
        initUI(view);
        addEvent(view);
//        loadGroup();
        if (type==0){//关注

        }else {//推荐
            loadData();
        }
    }

    private void loadGroup() {
        helper.showProgressDialog("");

    }

    private void initUI(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter=new CommonAdapter<MessageEntity>(getContext(),R.layout.item_square_news) {
            @Override
            public void convert(ViewHolder holder, MessageEntity entity, int position) {

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

    @OnClick({R2.id.message_type, R2.id.message_search})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.message_type) {
        } else if (id == R.id.message_search) {
        }
    }
}