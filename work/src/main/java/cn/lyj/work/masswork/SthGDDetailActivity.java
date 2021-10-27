package cn.lyj.work.masswork;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import allen.frame.entry.Response;
import allen.frame.entry.Type;
import allen.frame.net.Callback;
import allen.frame.net.Http;
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
import cn.lyj.work.entry.Feek;
import cn.lyj.work.entry.HfEntry;
import cn.lyj.work.entry.SthEntry;

public class SthGDDetailActivity extends AllenBaseActivity {

    private String id,uCode,utype,uid;
    private Toolbar bar;
    private Map<String, Object> map;
    private SthEntry entry;
    private RecyclerView xlv,clv;
    private BllcAdapter adapter;
    private NestedScrollView scroll;
    private AppCompatTextView numerat,slsjat,sldwat,slrat,fyrat,sexat,idat,phoneat,fyqdat,rklxat,jtzzat,jjcdat,sxlxat,sjlyat,fysxat;
    private AppCompatTextView fkdw,fkfs,fkr,fksj,fkms,pjat;
    private LinearLayoutCompat fjlayout,cbListLayout;
    private HfAdapter hfAdapter;
    private AppDialog appDialog;
    private String RegSate;
    private String trint="";

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.sth_gd_detail_layout;
    }

    @Override
    protected void initBar() {
        id = getIntent().getStringExtra(Constants.STH_ID);
        RegSate = getIntent().getStringExtra("RegSate");
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
        fkdw = findViewById(R.id.sth_fkdw);
        fkfs = findViewById(R.id.sth_fkfs);
        fkr = findViewById(R.id.sth_fkr);
        fksj = findViewById(R.id.sth_fksj);
        fkms = findViewById(R.id.sth_fkms);
        pjat = findViewById(R.id.sth_pj);
        xlv = findViewById(R.id.bllc_list);
        xlv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new BllcAdapter(context,R.layout.sth_bllc_item_layout);
        xlv.setAdapter(adapter);
        initCbListUI();
        if("1".equals(RegSate)){
            findViewById(R.id.gd_bt).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.gd_bt).setVisibility(View.VISIBLE);
        }
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
        findViewById(R.id.gd_bt).setOnClickListener(l);
    }

    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            if (v.getId() == R.id.gd_bt) {
                MsgUtils.showMDMessage(context, "确认归档吗?", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gcsth();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            v.setEnabled(true);
        }
    };

    private boolean checkIsOk(){
        /*if(StringUtils.empty(cbnr)){
            MsgUtils.showMDMessage(context,"请输入催办内容!");
            return false;
        }*/
        return true;
    }

    private void loadData(){
        Http.with(this).url("").parameters(new Object[]{}).enqueue(new Callback() {
            @Override
            public void success(Object data) {

            }

            @Override
            public void fail(Response response) {

            }
        }).get();
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

    private void gcsth(){
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                AppDataHelper.init().gdSth(handler,1,uCode,id,trint);
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

    private void setFkxx(Feek entry){
        fkdw.setText(entry.getDwname());
        fkfs.setText(entry.getFkfs());
        fkr.setText(entry.getFkr());
        fksj.setText(entry.getFksj());
        fkms.setText(entry.getFkms());
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
                    String pj = (String) map.get("pj");
                    pjat.setText(StringUtils.empty(pj)?"未评价":pj);
                    setFjData((ArrayList<Type>) map.get("fj"));
                    setFkxx((Feek) map.get("fk"));
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
