package cn.lyjj.thepublic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.AllenChoiceUnitsActivity;
import allen.frame.adapter.AllenFileShowAdapter;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.lyjj.thepublic.adapter.SthProgressAdapter;
import cn.lyjj.thepublic.api.TipApi;
import cn.lyjj.thepublic.entry.SthEntry;
import cn.lyjj.thepublic.entry.SthProgress;

public class TipOffInfoActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tip_info_dw)
    AppCompatTextView tipInfoDw;
    @BindView(R2.id.tip_info_grid)
    AppCompatTextView tipInfoGrid;
    @BindView(R2.id.tip_info_name)
    AppCompatTextView tipInfoName;
    @BindView(R2.id.tip_info_sex)
    AppCompatTextView tipInfoSex;
    @BindView(R2.id.tip_info_phone)
    AppCompatTextView tipInfoPhone;
    @BindView(R2.id.tip_info_idno)
    AppCompatTextView tipInfoIdno;
    @BindView(R2.id.tip_info_address)
    AppCompatTextView tipInfoAddress;
    @BindView(R2.id.tip_info_content)
    AppCompatTextView tipInfoContent;
    @BindView(R2.id.tip_info_file)
    RecyclerView tipInfoFile;
    @BindView(R2.id.tip_info_progress)
    RecyclerView tipInfoProgress;
    private AllenFileShowAdapter fileChoiceAdapter;
    private long id;
    private SthProgressAdapter progressAdapter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.tipoff_info;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"爆料详情",true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==Constants.KeyTokenFlag){
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                loadInfo();
                loadProgress();
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        id = getIntent().getLongExtra(Constants.Key_1,0);
        GridLayoutManager manager = new GridLayoutManager(context,4);
        tipInfoFile.setLayoutManager(manager);
        fileChoiceAdapter = new AllenFileShowAdapter();
        tipInfoFile.setAdapter(fileChoiceAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tipInfoProgress.setLayoutManager(layoutManager);
        progressAdapter = new SthProgressAdapter();
        tipInfoProgress.setAdapter(progressAdapter);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadInfo();
        loadProgress();
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                finish();
                v.setEnabled(true);
            }
        });
    }

    private void loadInfo(){
        Https.with(this).url(TipApi.TipDetail).addParam("id",id).get().enqueue(new Callback<SthEntry>() {
            @Override
            public void success(SthEntry data) {
                actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                if(data!=null){
                    tipInfoDw.setText(data.getOrg().getOrgName());
                    tipInfoGrid.setText(data.getGid());
                    tipInfoName.setText(data.getName());
                    tipInfoSex.setText(data.getSex());
                    tipInfoPhone.setText(data.getPhone());
                    tipInfoIdno.setText(StringUtils.hideStr(data.getIdNumber(),7,14,"*"));
                    tipInfoAddress.setText(data.getAddress());
                    tipInfoContent.setText(data.getContent());
                    fileChoiceAdapter.setData(data.getFiles());
                }
            }

            @Override
            public void token() {
                actHelper.tokenErro2Login(TipOffInfoActivity.this);
            }

            @Override
            public void fail(Response response) {
                MsgUtils.showShortToast(context,response.getMsg());
                finish();
            }
        });
    }
    private void loadProgress(){
        Https.with(this).url(TipApi.TipProgress).addParam("appealId",id).get().enqueue(new Callback<List<SthProgress>>() {
            @Override
            public void success(List<SthProgress> data) {
                progressAdapter.setList(data);
            }

            @Override
            public void fail(Response response) {

            }
        });
    }
}
