package cn.lyj.thepublic.main;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.VoteAdapter;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.VoteAnswerEntity;
import cn.lyj.thepublic.entry.VoteEntity;
import cn.lyj.thepublic.entry.WjdcEntity;

public class VoteActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar bar;
    @BindView(R2.id.vote_end)
    AppCompatTextView voteEnd;
    @BindView(R2.id.vote_status)
    AppCompatTextView voteStatus;
    @BindView(R2.id.vote_info)
    AppCompatTextView voteInfo;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.vote_result)
    AppCompatTextView voteResult;
    @BindView(R2.id.scroll)
    NestedScrollView scroll;
    @BindView(R2.id.ok_bt)
    AppCompatButton okBt;
    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.tv_vote_title)
    AppCompatTextView tvVoteTitle;

    private VoteAdapter adapter;
    private List<VoteEntity.ItemListBean> list;
    private VoteEntity.ItemListBean entry;
    private String id;
    private int uid;
    private WjdcEntity wjListBean;

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
        setToolbarTitle(bar, "问卷调查",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        wjListBean = (WjdcEntity) getIntent().getSerializableExtra("Wjdc");
        uid = actHelper.getSharedPreferences().getInt(Constants.UserId, 0);
        id = wjListBean.getPollId();
        tvVoteTitle.setText("#" + wjListBean.getPollTitle() + "#");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new VoteAdapter();
        rv.setAdapter(adapter);
        voteInfo.setText(wjListBean.getTextInfo().getTextContent());
        voteEnd.setText("结束时间:" + wjListBean.getPollEndtime());
        loadData();
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
        Https.with(this).url(API._getWjdcInfo).addParam("pollId", wjListBean.getPollId()).get().enqueue(new Callback<VoteEntity>() {

            @Override
            public void success(VoteEntity data) {
                list=data.getItemList();
                adapter.setList(list);
            }

            @Override
            public void fail(Response response) {

            }
        });
    }



    @OnClick(R2.id.ok_bt)
    public void onViewClicked() {
        List<VoteAnswerEntity> answerEntities=new ArrayList<>();
        for (VoteEntity.ItemListBean vote:list
             ) {
            VoteAnswerEntity answerEntity=new VoteAnswerEntity();
            answerEntity.setPollId(vote.getPollId());
            answerEntity.setItemId(vote.getItemId());
            if (vote.getItemType().equals("2")||vote.getItemType().equals("3")){
                if (vote.getChoiceID()==null|| StringUtils.empty(vote.getChoiceID())){
                    MsgUtils.showMDMessage(context,"您有未作答的题，请作答后提交！");
                    return;
                }else {
                    answerEntity.setItemValue(vote.getChoiceID());
                }
            }else {
                if (vote.getAnswer()==null||StringUtils.empty(vote.getAnswer())){
                    MsgUtils.showMDMessage(context,"您有未作答的题，请作答后提交！");
                    return;
                }else {
                    answerEntity.setItemValue(vote.getAnswer());
                }
            }
            answerEntities.add(answerEntity);
            Gson gson=new Gson();
            String json=gson.toJson(answerEntities);

            submit(json);
        }

    }

    private void submit( String  json) {
        showProgressDialog("");
        Https.with(this).url(API._submitAnswer).addJsons(json).post().enqueue(new Callback() {
            @Override
            public void success(Object data) {
                dismissProgressDialog();
                MsgUtils.showLongToast(context,"提交成功!");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void fail(Response response) {
                dismissProgressDialog();
                MsgUtils.showLongToast(context,response.getMsg());
            }
        });
    }

}
