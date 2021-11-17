package cn.lyj.thepublic.square;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.HtmlImageUtil.GlideImageGetter;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.adapter.DiscussAdapter;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.entry.Discuss;
import cn.lyj.thepublic.entry.SquareMessage;

public class NewsDetailActivity extends AllenBaseActivity {

    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.detail_title)
    AppCompatTextView detailTitle;
    @BindView(R2.id.detail_date)
    AppCompatTextView detailDate;
    @BindView(R2.id.detail_sourse)
    AppCompatTextView detailSourse;
    @BindView(R2.id.detail_con)
    AppCompatTextView detailCon;
    @BindView(R2.id.rv_discuss)
    RecyclerView rvDiscuss;
    @BindView(R2.id.tv_gz)
    AppCompatTextView tvGz;
    @BindView(R2.id.tv_discuss)
    AppCompatTextView tvDiscuss;
    @BindView(R2.id.tv_zan)
    AppCompatTextView tvZan;
    @BindView(R2.id.discuss_keyboard)
    AppCompatImageView discussKeyboard;
    @BindView(R2.id.discuss_input)
    AppCompatEditText discussInput;
    @BindView(R2.id.discuss_send)
    AppCompatTextView discussSend;
    @BindView(R2.id.keyboard_layout)
    LinearLayoutCompat keyboardLayout;
    @BindView(R2.id.layout_gn)
    LinearLayoutCompat layoutGn;
    private String userId;
    private DiscussAdapter discussAdapter;
    private SquareMessage squareMessage;
    private List<Discuss> list;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar, "广场", true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        userId=shared.getString(Constants.UserId,"");
        squareMessage = (SquareMessage) getIntent().getSerializableExtra("square");
        detailTitle.setText(squareMessage.getServiceTitle());
        detailDate.setText(StringUtils.empty(squareMessage.getCreateTime()) ? squareMessage.getCreateTime() : squareMessage.getCreateTime().substring(0, 10));
        detailSourse.setText("来源:" + squareMessage.getServiceType());
        detailCon.setText(Html.fromHtml(StringUtils.null2Empty(squareMessage.getServiceContent()),
                new GlideImageGetter(context, detailCon), null));
        if (squareMessage.getIsLike() == 1) {
            Drawable drawable = getResources().getDrawable(R.mipmap.square_zan_blue);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable, null, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.square_zan_gray);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvZan.setCompoundDrawables(drawable, null, null, null);
        }
        if (squareMessage.getIsConcern() == 1) {
            Drawable drawable = getResources().getDrawable(R.mipmap.user_gz);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvGz.setCompoundDrawables(drawable, null, null, null);
            tvGz.setText("已关注");
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.square_add_gz);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvGz.setCompoundDrawables(drawable, null, null, null);
        }
        initAdapter();
        loadData();
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDiscuss.setLayoutManager(manager);
        discussAdapter = new DiscussAdapter(userId);
        rvDiscuss.setAdapter(discussAdapter);
    }

    private void loadData() {
        Https.with(this).url(API._getDiscussList).addParam("serviceId", squareMessage.getServiceId()).get().enqueue(new Callback<List<Discuss>>() {
            @Override
            public void success(List<Discuss> data) {
                list = data;
                discussAdapter.setList(list);
            }

            @Override
            public void fail(Response response) {

            }
        });
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        discussAdapter.setOnItemClickListener(new DiscussAdapter.OnItemClickListener() {
            @Override
            public void itemDelClick(View v, Discuss Discuss) {
                delDiscuss(Discuss.getCommentId());
            }
        });
    }

    /**
     * commentContent：评论内容
     * serviceId：文章id
     * superCommentId：上级评论id，第一级评论传空
     * @param content
     */
    private void sendDiscuss(String content) {
        Https.with(this).url(API._addDiscuss)
                .addParam("commentContent",content)
                .addParam("serviceId",squareMessage.getServiceId())
                .addParam("superCommentId","")
                .post()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        layoutGn.setVisibility(View.VISIBLE);
                        keyboardLayout.setVisibility(View.GONE);
                        MsgUtils.showLongToast(context, "评论成功！");
                        discussInput.setText("");
                        loadData();
                    }

                    @Override
                    public void fail(Response response) {
                        layoutGn.setVisibility(View.VISIBLE);
                        keyboardLayout.setVisibility(View.GONE);
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });

    }

    private void addLike() {
        Https.with(this).url(API._addZan)
                .addParam("serviceId",squareMessage.getServiceId())
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        squareMessage.setIsLike(1);
                        Drawable drawable = getResources().getDrawable(R.mipmap.square_zan_blue);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvZan.setCompoundDrawables(drawable, null, null, null);
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });
    }
    private void cancelLike() {
        Https.with(this).url(API._delZan)
                .addParam("serviceId",squareMessage.getServiceId())
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        squareMessage.setIsLike(0);
                        Drawable drawable = getResources().getDrawable(R.mipmap.square_zan_gray);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvZan.setCompoundDrawables(drawable, null, null, null);
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });
    }

    private void addGz() {
        Https.with(this).url(API._addGuanZhu)
                .addParam("serviceId",squareMessage.getServiceId())
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        squareMessage.setIsConcern(1);
                        Drawable drawable = getResources().getDrawable(R.mipmap.user_gz);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvGz.setCompoundDrawables(drawable, null, null, null);
                        tvGz.setText("已关注");
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });
    }

    private void delGz() {
        Https.with(this).url(API._cancelGuanZhu)
                .addParam("serviceId",squareMessage.getServiceId())
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        squareMessage.setIsConcern(0);
                        Drawable drawable = getResources().getDrawable(R.mipmap.square_add_gz);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvGz.setCompoundDrawables(drawable, null, null, null);
                        tvGz.setText("关注");
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });
    }
    private void delDiscuss(String ID) {
        Https.with(this).url(API._delDiscuss)
                .addParam("commentId",ID)
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showLongToast(context, "删除成功！");
                        loadData();
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(context, response.getMsg());
                    }
                });
    }


    @OnClick({R2.id.tv_gz, R2.id.tv_discuss, R2.id.tv_zan, R2.id.discuss_send})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_gz) {
            if (squareMessage.getIsConcern()==0){
                addGz();
            }else {
                delGz();
            }
        } else if (viewId == R.id.tv_discuss) {
            layoutGn.setVisibility(View.GONE);
            keyboardLayout.setVisibility(View.VISIBLE);
        } else if (viewId == R.id.tv_zan) {
            if (squareMessage.getIsLike()==0){
                addLike();
            }else {
                cancelLike();
            }
        } else if (viewId == R.id.discuss_send) {
            String content = discussInput.getText().toString();
            if (StringUtils.notEmpty(content)) {
                sendDiscuss(content);
            }
        }
    }

}
