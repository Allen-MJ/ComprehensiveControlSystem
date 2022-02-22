package cn.lyj.core.kaohe;

import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyj.core.R;
import cn.lyj.core.R2;
import cn.lyj.core.adapter.KaoheRelateAdapter;
import cn.lyj.core.api.CoreApi;
import cn.lyj.core.entry.KaoheEntry;

public class AppraiseInfoActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.info_title)
    AppCompatTextView infoTitle;
    @BindView(R2.id.examine_title)
    AppCompatTextView examineTitle;
    @BindView(R2.id.examine_type)
    AppCompatTextView examineType;
    @BindView(R2.id.examine_date)
    AppCompatTextView examineDate;
    @BindView(R2.id.examine_creat)
    AppCompatTextView examineCreat;
    @BindView(R2.id.examine_update)
    AppCompatTextView examineUpdate;
    @BindView(R2.id.examine_org)
    AppCompatTextView examineOrg;
    @BindView(R2.id.examine_score)
    AppCompatTextView examineScore;
    @BindView(R2.id.examine_content)
    AppCompatTextView examineContent;
    @BindView(R2.id.examine_relation)
    RecyclerView examineRelation;
    private KaoheEntry entry;
    private KaoheRelateAdapter adapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.core_kaohe_appraise_info;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"我的考核",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        entry = (KaoheEntry) getIntent().getSerializableExtra(Constants.ObjectFirst);
        if(entry!=null){
            infoTitle.setText(entry.getOrgInfo().getGenericName());
            examineTitle.setText(entry.getExamineTitle());
            examineType.setText(entry.getExamineType());
            examineDate.setText(entry.getExamineDate());
            examineCreat.setText(entry.getCreateTime());
            examineUpdate.setText(entry.getUpdateTime());
            examineOrg.setText(entry.getRules().getOrgInfo().getGenericName());
            examineScore.setText(String.format("d%",entry.getRules().getExamineSource()));
            examineContent.setText(entry.getRules().getExamineContent());
            GridLayoutManager manager = new GridLayoutManager(this,2);
            examineRelation.setLayoutManager(manager);
            adapter = new KaoheRelateAdapter();
            examineRelation.setAdapter(adapter);
            adapter.setList(entry.getExamineRelations());
        }
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter.setOnItemClickListener(new KaoheRelateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, KaoheEntry.Relation entry, int position) {
                finishkh(entry);
            }
        });
    }

    private void finishkh(final KaoheEntry.Relation entry){
        showProgressDialog("正在处理,请稍等...");
        Https.with(this).url(CoreApi.finishkh).addParam("id",entry.getExamineId()).put().enqueue(new Callback<Object>() {

            @Override
            public void success(Object data) {
                dismissProgressDialog();
                entry.setFinish(true);
                entry.setFinishTime(getDate());
                adapter.notifyDataSetChanged();
                MsgUtils.showMDMessage(context,"处理成功!");
            }

            @Override
            public void fail(Response response) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context,"处理成功!");
            }
        });
    }

    private String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

}
