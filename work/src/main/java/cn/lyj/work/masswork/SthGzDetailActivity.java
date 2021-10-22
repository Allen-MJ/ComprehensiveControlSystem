package cn.lyj.work.masswork;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.entry.Type;
import allen.frame.tools.AppDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.tools.UpdateInterface;
import cn.lyj.work.R;
import cn.lyj.work.adapter.BllcAdapter;
import cn.lyj.work.adapter.HfAdapter;
import cn.lyj.work.entry.BllcEntry;
import cn.lyj.work.entry.HfEntry;
import cn.lyj.work.entry.SthEntry;

public class SthGzDetailActivity extends AllenBaseActivity {

    private String id,uCode,utype,uid;
    private Toolbar bar;
    private Map<String, Object> map;
    private SthEntry entry;
    private RecyclerView xlv,clv;
    private BllcAdapter adapter;
    private NestedScrollView scroll;
    private AppCompatTextView numerat,slsjat,sldwat,slrat,fyrat,sexat,idat,phoneat,fyqdat,rklxat,jtzzat,jjcdat,sxlxat,sjlyat,fysxat;
    private LinearLayoutCompat fjlayout,cbListLayout;
    private HfAdapter hfAdapter;
    private AppDialog appDialog;
    private AppCompatEditText cbnrae;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.sth_db_detail_layout;
    }

    @Override
    protected void initBar() {
        id = getIntent().getStringExtra(Constants.STH_ID);
        uCode = actHelper.getSharedPreferences().getString(Constants.USER_UNITCODE,"");
        utype = actHelper.getSharedPreferences().getString(Constants.USER_UNITTYPE,"");
        uid = actHelper.getSharedPreferences().getString(Constants.USER_ID,"");
        bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        appDialog = new AppDialog(context);
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        scroll = findViewById(R.id.scroll);
        numerat = findViewById(R.id.sth_detail_number);
        slsjat = findViewById(R.id.sth_detail_slsj);
        sldwat = findViewById(R.id.sth_detail_sldw);
        slrat = findViewById(R.id.sth_detail_slr);
        fyrat = findViewById(R.id.sth_detail_fyr);
        phoneat = findViewById(R.id.sth_detail_phone);
        sexat = findViewById(R.id.sth_detail_sex);
        idat = findViewById(R.id.sth_detail_id);
        fyqdat = findViewById(R.id.sth_detail_fyqd);
        rklxat = findViewById(R.id.sth_detail_rklx);
        jtzzat = findViewById(R.id.sth_detail_jtzz);
        jjcdat = findViewById(R.id.sth_detail_jjcd);
        sxlxat = findViewById(R.id.sth_detail_sxlx);
        sjlyat = findViewById(R.id.sth_detail_sjly);
        fysxat = findViewById(R.id.sth_detail_fysx);
        fjlayout = findViewById(R.id.sth_detail_fj);
        cbnrae = findViewById(R.id.cb_cbnr);
        xlv = findViewById(R.id.bllc_list);
        xlv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new BllcAdapter(context,R.layout.sth_bllc_item_layout);
        xlv.setAdapter(adapter);
        initCbListUI();
        loadData();
    }

    private void initCbListUI(){
        cbListLayout = findViewById(R.id.cb_list_layout);
        clv = findViewById(R.id.cb_list);
        clv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        hfAdapter = new HfAdapter(context,R.layout.sth_hf_item_layout,false);
        clv.setAdapter(hfAdapter);
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        appDialog.setUpdateInterface(updateInterface);
        findViewById(R.id.cb_bt).setOnClickListener(l);
        findViewById(R.id.cb_ok_bt).setOnClickListener(l);
        findViewById(R.id.cb_no_bt).setOnClickListener(l);
    }

    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            int vId = v.getId();
            if (vId == R.id.cb_bt) {
                findViewById(R.id.cz_layout).setVisibility(View.GONE);
                findViewById(R.id.cb_layout).setVisibility(View.VISIBLE);
            } else if (vId == R.id.cb_no_bt) {
                findViewById(R.id.cz_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.cb_layout).setVisibility(View.GONE);
            } else if (vId == R.id.cb_ok_bt) {
                if (checkIsOk()) {
                    cbGz();
                }
            }
            v.setClickable(true);
        }
    };

    private String cbnr;
    private boolean checkIsOk(){
        cbnr = cbnrae.getText().toString().trim();
        if(StringUtils.empty(cbnr)){
            MsgUtils.showMDMessage(context,"请输入催办内容!");
            return false;
        }
        return true;
    }

    private void cbGz(){
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                AppDataHelper.init().cbGz(handler,1,uCode,uid,id,cbnr,utype);
            }
        }).start();
    }

    private void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                map = AppDataHelper.init().getSthdetail(handler, id,uCode,utype);
//                if(map!=null){
//                    handler.sendEmptyMessage(0);
//                }
            }
        }).start();
    }
    private void setFjData(ArrayList<Type> list){
        fjlayout.removeAllViews();
        int len = list==null?0:list.size();
        for(int i=0;i<len;i++){
            TextView item = new TextView(context);
            item.setTextAppearance(context,R.style.SthDbFJContTheme);
            item.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
            item.setText(list.get(i).getName());
            final String name = list.get(i).getName();
            final String url = list.get(i).getRemark();
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downLoad(name, url);
                }
            });
            fjlayout.addView(item);
        }
    }

    private void setCz(Map<String, Boolean> map){
        findViewById(R.id.bj_bt).setVisibility(View.GONE);
        findViewById(R.id.zb_bt).setVisibility(View.GONE);
        findViewById(R.id.bl_bt).setVisibility(View.GONE);
        findViewById(R.id.yqsq_bt).setVisibility(View.GONE);
        findViewById(R.id.cqbl_bt).setVisibility(View.GONE);
        if(map.get("cb")){
            findViewById(R.id.cb_bt).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.cb_bt).setVisibility(View.GONE);
        }
    }

    private void setHfData(ArrayList<HfEntry> list){
        int len = list==null?0:list.size();
        if(len>0){
            cbListLayout.setVisibility(View.VISIBLE);
            hfAdapter.setData(list);
        }else{
            cbListLayout.setVisibility(View.GONE);
        }
    }

    UpdateInterface updateInterface = new UpdateInterface() {
        @Override
        public void openFile(String path, String msg) {
            // TODO Auto-generated method stub
            super.openFile(path, msg);
            FileIntent.openFile(context, new File(path));
        }
    };

    private void downLoad(String name, String url) {
        HashMap<String, String> info = new HashMap<String, String>();
        info.put("title", "提示");//new
        info.put("name", name);
        info.put("url", url);
        info.put("dir", Constants.APPFILE_DOWNLOAD);
        Logger.e("下载url", url);
        appDialog.OpenFileNewDialog(info);//new
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                    entry = (SthEntry) map.get("detail");
                    numerat.setText(entry.getNo());
//                    slsjat.setText(BqDateUtils.getDatenyr(entry.getSldate()));
                    sldwat.setText(entry.getSldw());
                    slrat.setText(entry.getSlr());
                    fyrat.setText(entry.getFyr());
                    sexat.setText(entry.getSex());
                    idat.setText(entry.getSfz());
                    phoneat.setText(entry.getPhone());
                    fyqdat.setText(entry.getFyqd());
                    rklxat.setText(entry.getRklx());
                    jtzzat.setText(entry.getJtzz());
                    jjcdat.setText(entry.getJjcd());
                    sxlxat.setText(entry.getSxlx());
                    sjlyat.setText(entry.getSjly());
                    fysxat.setText(entry.getFysx());
                    setFjData((ArrayList<Type>) map.get("fj"));
                    setCz((Map<String, Boolean>) map.get("cz"));
                    adapter.setData((List<BllcEntry>) map.get("lc"));
                    setHfData((ArrayList<HfEntry>) map.get("hf"));
                    post(new Runnable() {
                        @Override
                        public void run() {
                            scroll.scrollTo(0,0);
                        }
                    });
                    break;
                case 1:
                    actHelper.dismissProgressDialog();
                    setResult(RESULT_OK,getIntent());
                    finish();
                    break;
                case -1:
                    actHelper.dismissProgressDialog();
                    MsgUtils.showMDMessage(context, (String) msg.obj);
                    break;
            }
        }
    };
}
