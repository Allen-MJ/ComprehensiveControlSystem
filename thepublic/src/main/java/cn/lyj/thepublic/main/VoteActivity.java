package cn.lyj.thepublic.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.adapter.VoteAdapter;

public class VoteActivity extends AllenBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.vote_end)
    AppCompatTextView voteEnd;
    @BindView(R.id.vote_status)
    AppCompatTextView voteStatus;
    @BindView(R.id.vote_info)
    AppCompatTextView voteInfo;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.vote_result)
    AppCompatTextView voteResult;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.ok_bt)
    AppCompatButton okBt;
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.tv_vote_title)
    AppCompatTextView tvVoteTitle;

    private VoteAdapter adapter;
    private List<Vote.TgListBean> list;
    private Vote entry;
    private String id;
    private int statu;
    private int uid;
    private YsjdEntity.WjListBean wjListBean;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_vote;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        setToolbarTitle(bar, getTitle());
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        uid = actHelper.getSharedPreferences().getInt(Constants.UserId, 0);
        wjListBean = (YsjdEntity.WjListBean) getIntent().getSerializableExtra("Wjdc");
        id = wjListBean.getID();
        tvVoteTitle.setText("#"+wjListBean.getBt()+"#");
        boolean isManager = getIntent().getBooleanExtra(Constants.Key_1, false);
        if (isManager) {
            statu = 1;
            okBt.setVisibility(View.GONE);
        } else {
            statu = 0;
            if (wjListBean.getDtcount() > 0) {
                statu = 1;
            }
            if (wjListBean.getEndday() < 0) {
                statu = 1;
                voteStatus.setText("已结束");
            }
            if (statu == 0) {
                okBt.setVisibility(View.VISIBLE);
            } else {
                okBt.setVisibility(View.GONE);
            }
        }
        loadData();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new VoteAdapter(statu);
        rv.setAdapter(adapter);
        voteInfo.setText(wjListBean.getBt());
        voteEnd.setText("结束时间:"+wjListBean.getEndTime());
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {
        WebHelper.init().getWjdcInfo(id, new HttpCallBack<List<Vote>>() {
            @Override
            public void onSuccess(List<Vote> respone) {
                entry = respone.get(0);
                list = entry.getTgList();
                adapter.setList(list, wjListBean.getDtcount());
            }

            @Override
            public void onTodo(Respone respone) {

            }

            @Override
            public void tokenErro(Respone respone) {

            }

            @Override
            public void onFailed(Respone respone) {
                MsgUtils.showLongToast(context, respone.getMessage());
            }
        });
    }

    @OnClick(R.id.ok_bt)
    public void onViewClicked() {
        List<Vote.TgListBean> chooseBean = adapter.getList();
        List<Vote.TgListBean.XxListBean> chooseOption = new ArrayList<>();
        for (Vote.TgListBean tg : chooseBean) {
            chooseOption.addAll(tg.getChoice());
        }
        if (chooseOption.isEmpty()) {
            MsgUtils.showMDMessage(context, "您还没有进行投票,请选择后再提交!");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Vote.TgListBean.XxListBean xx : chooseOption
        ) {
            stringBuffer.append(xx.getXxZm() + "_" + xx.getTgID() + ",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        Logger.e("choose:------", stringBuffer.toString());
        showProgressDialog("正在提交，请稍候...");
        submit(stringBuffer.toString());
    }

    private void submit(String wjdc) {
        WebHelper.init().submitVote(wjdc, entry.getBtID(), uid, new HttpCallBack() {
            @Override
            public void onSuccess(Object respone) {

            }

            @Override
            public void onTodo(Respone respone) {
                dismissProgressDialog();
                MsgUtils.showLongToast(context, "提交成功！");
                finish();
            }

            @Override
            public void tokenErro(Respone respone) {

            }

            @Override
            public void onFailed(Respone respone) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context, respone.getMessage());
            }
        });
    }

}
