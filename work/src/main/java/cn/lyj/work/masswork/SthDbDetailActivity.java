package cn.lyj.work.masswork;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
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
import allen.frame.entry.Response;
import allen.frame.entry.Type;
import allen.frame.net.Callback;
import allen.frame.net.Http;
import allen.frame.tools.AppDialog;
import allen.frame.tools.ChoiceTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.DateUtils;
import allen.frame.tools.FileIntent;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.tools.UpdateInterface;
import cn.lyj.work.R;
import cn.lyj.work.adapter.BllcAdapter;
import cn.lyj.work.adapter.HfAdapter;
import cn.lyj.work.entry.HfEntry;
import cn.lyj.work.entry.SthEntry;

public class SthDbDetailActivity extends AllenBaseActivity {

    private String id,uCode,uName,utype,uid;
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

    private String sxlxid,sjlyid;
    private boolean isbjxz = false;
    //办结反馈控件
    private AppCompatCheckBox isjjcb,isdbcb;
    private AppCompatTextView bjsxlx,bjsjly,fkdwat,fkfsat,fksjat;
    private AppCompatEditText fkbljgae,fkryae,fkmsae;
    private String isjj = "0",isdb = "0",fkbljg,fkfs,fkry,fksj,fkms;

    //转办控件
    private AppCompatTextView zbsxlx,zbsjly,zjdw,zbslr,xbdw;
    private AppCompatCheckBox isxbac;
    private AppCompatEditText blsxae,blyjae;
    private String zbcode,umid,xbcode,isxb="0",blsx,blyj;

    private AppCompatEditText xbblae;
    private String xbbl;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.sth_db_detail_layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                zbcode = data.getStringExtra("codes");
                zjdw.setText(data.getStringExtra("names"));
                zbslr.setText("");
                umid = "";
                if (utype.equals("1")) {
                    findViewById(R.id.zb_xb_layout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.zb_xb_layout).setVisibility(View.GONE);
                }
            } else if (requestCode == 2) {
                xbcode = data.getStringExtra("codes");
                xbdw.setText(data.getStringExtra("names"));
            }
        }
    }

    @Override
    protected void initBar() {
        id = getIntent().getStringExtra(Constants.STH_ID);
//        uCode = actHelper.getSharedPreferences().getString(Constants.USER_UNITCODE,"");
//        uName = actHelper.getSharedPreferences().getString(Constants.USER_UNITNAME,"");
//        utype = actHelper.getSharedPreferences().getString(Constants.USER_UNITTYPE,"");
//        uid = actHelper.getSharedPreferences().getString(Constants.USER_ID,"");
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
        initBjUI();
        initZbUI();
        initXbUI();
        initCbListUI();
        xlv = findViewById(R.id.bllc_list);
        xlv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new BllcAdapter(context,R.layout.sth_bllc_item_layout);
        xlv.setAdapter(adapter);

        loadData();
    }
    //办结初始化
    private void initBjUI(){
        bjsxlx = findViewById(R.id.bj_sxlx);
        bjsjly = findViewById(R.id.bj_sjly);
        fkdwat = findViewById(R.id.bj_fkdw);
        fkfsat = findViewById(R.id.bj_fkfs);
        fksjat = findViewById(R.id.bj_fksj);
        isjjcb = findViewById(R.id.bj_isjjsx);
        isdbcb = findViewById(R.id.bj_islddb);
        fkbljgae = findViewById(R.id.bj_bljg);
        fkryae = findViewById(R.id.bj_fkry);
        fkmsae = findViewById(R.id.bj_fkms);
    }

    private void initZbUI(){
        zbsxlx = findViewById(R.id.zb_sxlx);
        zbsjly = findViewById(R.id.zb_sjly);
        zjdw = findViewById(R.id.zb_dwname);
        zbslr = findViewById(R.id.zb_slrname);
        blsxae = findViewById(R.id.zb_blsx);
        blyjae = findViewById(R.id.zb_blyj);
        isxbac = findViewById(R.id.zb_isxb);
        xbdw = findViewById(R.id.zb_sbdw_names);
    }
    private void initXbUI(){
        xbblae = findViewById(R.id.xbbl_blyj);
    }

    private void initCbListUI(){
        cbListLayout = findViewById(R.id.cb_list_layout);
        clv = findViewById(R.id.cb_list);
        clv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        hfAdapter = new HfAdapter(context,R.layout.sth_hf_item_layout,true);
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
        findViewById(R.id.bj_bt).setOnClickListener(l);
        bjsxlx.setOnClickListener(l);
        bjsjly.setOnClickListener(l);
        findViewById(R.id.bj_ok_bt).setOnClickListener(l);
        findViewById(R.id.bj_no_bt).setOnClickListener(l);
        findViewById(R.id.zb_bt).setOnClickListener(l);
        zbsxlx.setOnClickListener(l);
        zbslr.setOnClickListener(l);
        zbsjly.setOnClickListener(l);
        findViewById(R.id.zb_ok_bt).setOnClickListener(l);
        findViewById(R.id.zb_no_bt).setOnClickListener(l);
        findViewById(R.id.bl_bt).setOnClickListener(l);
        findViewById(R.id.xbbl_ok_bt).setOnClickListener(l);
        findViewById(R.id.xbbl_no_bt).setOnClickListener(l);
        isjjcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isjj = "0";
                }else{
                    isjj = "1";
                }
            }
        });
        isdbcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isdb = "1";
                }else{
                    isdb = "0";
                }
            }
        });
        fkfsat.setOnClickListener(l);
        fksjat.setOnClickListener(l);
        isxbac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isxb = "1";
                    findViewById(R.id.xb_layout).setVisibility(View.VISIBLE);
                }else{
                    isxb = "0";
                    xbdw.setText("");
                    xbcode = "";
                    findViewById(R.id.xb_layout).setVisibility(View.GONE);
                }
            }
        });
        zjdw.setOnClickListener(l);
        xbdw.setOnClickListener(l);
        hfAdapter.setItemReplyLisener(new HfAdapter.OnItemReplyLisener() {
            @Override
            public void itemReplay(final int position, final HfEntry entry) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("回复");
                View view = LayoutInflater.from(context).inflate(R.layout.sth_cb_hf_layout,null,false);
                builder.setView(view);
                final AppCompatEditText eidt = view.findViewById(R.id.hf_edit);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String hf = eidt.getText().toString().trim();
                        if(StringUtils.empty(hf)){
                            MsgUtils.showMDMessage(context,"回复不能为空!");
                        }else{
                            cbHf(position,entry.getMessageId(),hf);
                        }

                    }
                }).show();
            }
        });
    }
    List<Type> fkfss;
    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            int vId = v.getId();
            if (vId == R.id.bj_bt) {
                isbjxz = true;
                fkdwat.setText(uName);
                sxlxid = entry.getSxlxId();
                bjsxlx.setText(entry.getSxlx());
                sjlyid = entry.getSjlyId();
                bjsjly.setText(entry.getSjly());
                findViewById(R.id.cz_layout).setVisibility(View.GONE);
                findViewById(R.id.bj_layout).setVisibility(View.VISIBLE);
            } else if (vId == R.id.bj_no_bt) {
                findViewById(R.id.cz_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.bj_layout).setVisibility(View.GONE);
            } else if (vId == R.id.bj_sxlx) {
                getSxlxList();
            } else if (vId == R.id.bj_sjly) {
                getSjlyList();
            } else if (vId == R.id.bj_ok_bt) {
                if (checkBjIsOok()) {
                    sxbanjie();
                }
            } else if (vId == R.id.bj_fkfs) {
                fkfss = new ArrayList<>();
                fkfss.add(new Type("0", "面对面"));
                fkfss.add(new Type("1", "电话"));
                fkfss.add(new Type("2", "书面"));
                fkfss.add(new Type("3", "其他"));
                ChoiceTypeDialog fkfsdialog = new ChoiceTypeDialog(context, handler, 4);
                fkfsdialog.showDialog("请选择反馈方式", fkfsat, fkfss);
            } else if (vId == R.id.bj_fksj) {
                DateUtils.doSetDateDialog(context, fksjat);
            } else if (vId == R.id.zb_bt) {
                isbjxz = false;
                sxlxid = entry.getSxlxId();
                zbsxlx.setText(entry.getSxlx());
                sjlyid = entry.getSjlyId();
                zbsjly.setText(entry.getSjly());
                findViewById(R.id.cz_layout).setVisibility(View.GONE);
                findViewById(R.id.zb_layout).setVisibility(View.VISIBLE);
                if (!utype.equals("1")) {
                    zbcode = "101";
                    zjdw.setText("璧泉街道");
                }
            } else if (vId == R.id.zb_sxlx) {
                getSxlxList();
            } else if (vId == R.id.zb_sjly) {
                getSjlyList();
            } else if (vId == R.id.zb_slrname) {
                getSlrList();
            } else if (vId == R.id.zb_no_bt) {
                findViewById(R.id.cz_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.zb_layout).setVisibility(View.GONE);
            } else if (vId == R.id.zb_ok_bt) {
                if (checkZbIsOk()) {
                    sxzhuanban();
                }
            } else if (vId == R.id.zb_dwname) {
                if (utype.equals("1")) {
//                    startActivityForResult(new Intent(context, ChoiceUnitsActivity.class).putExtra("more", false), 1);
                }
            } else if (vId == R.id.zb_sbdw_names) {
                if (utype.equals("1")) {
//                    startActivityForResult(new Intent(context, ChoiceUnitsActivity.class).putExtra("more", true).putExtra("ucodes", xbcode), 2);
                }
            } else if (vId == R.id.bl_bt) {
                findViewById(R.id.cz_layout).setVisibility(View.GONE);
                findViewById(R.id.xbbl_layout).setVisibility(View.VISIBLE);
            } else if (vId == R.id.xbbl_ok_bt) {
                if (checkBlIsOk()) {
                    sthbl();
                }
            } else if (vId == R.id.xbbl_no_bt) {
                findViewById(R.id.cz_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.xbbl_layout).setVisibility(View.GONE);
            }
            v.setClickable(true);
        }
    };

    private boolean checkBjIsOok(){
        fkbljg = fkbljgae.getText().toString().trim();
        fkry = fkryae.getText().toString().trim();
        fksj = fksjat.getText().toString().trim();
        fkms = fkmsae.getText().toString().trim();
        if(StringUtils.empty(sxlxid)){
            MsgUtils.showMDMessage(context,"请选择事项类型!");
            return false;
        }
        if(StringUtils.empty(sjlyid)){
            MsgUtils.showMDMessage(context,"请选择涉及领域!");
            return false;
        }
        if(StringUtils.empty(fkbljg)){
            MsgUtils.showMDMessage(context,"请输入办理结果!");
            return false;
        }
        if(StringUtils.empty(fkfs)){
            MsgUtils.showMDMessage(context,"请选择反馈方式!");
            return false;
        }
        if(StringUtils.empty(fkry)){
            MsgUtils.showMDMessage(context,"请填写反馈人员!");
            return false;
        }
        if(StringUtils.empty(fksj)){
            MsgUtils.showMDMessage(context,"请选择反馈时间!");
            return false;
        }
        if(StringUtils.empty(fkms)){
            MsgUtils.showMDMessage(context,"请输入反馈描述!");
            return false;
        }

        return true;
    }

    private List<Type> sxlxs;
    private void getSxlxList(){
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                sxlxs = AppDataHelper.init().getTypeList("12");
//                handler.sendEmptyMessage(13);
            }
        }).start();
    }
    private List<Type> sjlys;
    private void getSjlyList(){
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                sjlys = AppDataHelper.init().getTypeList("16");
//                handler.sendEmptyMessage(14);
            }
        }).start();
    }
    private List<Type> slrs;
    private void getSlrList(){
        if(StringUtils.empty(zbcode)){
            MsgUtils.showMDMessage(context,"请选择转交单位!");
            return ;
        }
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                slrs = AppDataHelper.init().getSlUserList(zbcode);
//                handler.sendEmptyMessage(16);
            }
        }).start();
    }

    //办结
    private void sxbanjie(){
        actHelper.showProgressDialog("");
        Http.with(this).url("").parameters(new Object[]{}).enqueue(new Callback() {
            @Override
            public void success(Object data) {

            }

            @Override
            public void fail(Response response) {

            }
        }).post();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AppDataHelper.init().sxbanjie(handler,1,id,sxlxid,sjlyid,uCode,fkbljg,isdb,isjj,fkfs,fkry,fkms,fksj);
//            }
//        }).start();
    }

    private boolean checkZbIsOk(){
        blsx = blsxae.getText().toString().trim();
        blyj = blyjae.getText().toString().trim();
        if(StringUtils.empty(sxlxid)){
            MsgUtils.showMDMessage(context,"请选择事项类型!");
            return false;
        }
        if(StringUtils.empty(sjlyid)){
            MsgUtils.showMDMessage(context,"请选择涉及领域!");
            return false;
        }
        if(StringUtils.empty(zbcode)){
            MsgUtils.showMDMessage(context,"请选择转交单位!");
            return false;
        }
        if(StringUtils.empty(umid)){
            MsgUtils.showMDMessage(context,"请选择受理人!");
            return false;
        }
        if(StringUtils.empty(blsx)){
            MsgUtils.showMDMessage(context,"请输入办理时限!");
            return false;
        }
        if(isxb.equals("1")){
            if(StringUtils.empty(xbcode)){
                MsgUtils.showMDMessage(context,"请选择转交单位!");
                return false;
            }
        }
        if(StringUtils.empty(blyj)){
            MsgUtils.showMDMessage(context,"请输入转办意见!");
            return false;
        }
        return true;
    }

    private void sxzhuanban(){
        actHelper.showProgressDialog("");
        Http.with(this).url("").parameters(new Object[]{}).enqueue(new Callback() {
            @Override
            public void success(Object data) {

            }

            @Override
            public void fail(Response response) {

            }
        }).post();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AppDataHelper.init().sxzhuanban(handler,2,id,sxlxid,sjlyid,uCode,umid,blyj,isxb,xbcode,zbcode,blsx);
//            }
//        }).start();
    }
    private boolean checkBlIsOk(){
        xbbl = xbblae.getText().toString().trim();
        if(StringUtils.empty(xbbl)){
            MsgUtils.showMDMessage(context,"请输入办理意见！");
            return false;
        }
        return true;
    }

    private void sthbl(){
        actHelper.showProgressDialog("");
        Http.with(this).url("").parameters(new Object[]{}).enqueue(new Callback() {
            @Override
            public void success(Object data) {

            }

            @Override
            public void fail(Response response) {

            }
        }).post();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AppDataHelper.init().sthbl(handler,3,uCode,id,xbbl);
//            }
//        }).start();
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

    private void cbHf(int index, String id, String msg){
        showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                AppDataHelper.init().GetCbReply(handler,id,msg,index);
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

    private void setHfData(ArrayList<HfEntry> list){
        int len = list==null?0:list.size();
        if(len>0){
            cbListLayout.setVisibility(View.VISIBLE);
            hfAdapter.setData(list);
        }else{
            cbListLayout.setVisibility(View.GONE);
        }
    }

    private void setCz(Map<String, Boolean> map){
        if(map.get("bj")){
            findViewById(R.id.bj_bt).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.bj_bt).setVisibility(View.GONE);
        }
        if(map.get("zb")){
            findViewById(R.id.zb_bt).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.zb_bt).setVisibility(View.GONE);
        }
        if(map.get("bl")){
            findViewById(R.id.bl_bt).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.bl_bt).setVisibility(View.GONE);
        }
//        if(map.get("sqyq")){
//            findViewById(R.id.yqsq_bt).setVisibility(View.VISIBLE);
//        }else{
            findViewById(R.id.yqsq_bt).setVisibility(View.GONE);
//        }
//        if(map.get("bljd")){
//            findViewById(R.id.cqbl_bt).setVisibility(View.VISIBLE);
//        }else{
            findViewById(R.id.cqbl_bt).setVisibility(View.GONE);
//        }
        findViewById(R.id.cb_bt).setVisibility(View.GONE);
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
//                    adapter.setData((List<BllcEntry>) map.get("lc"));
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
                case 2:
                    actHelper.dismissProgressDialog();
                    setResult(RESULT_OK,getIntent());
                    finish();
                    break;
                case -2:
                    actHelper.dismissProgressDialog();
                    MsgUtils.showMDMessage(context, (String) msg.obj);
                    break;
                case 3:
                    actHelper.dismissProgressDialog();
                    setResult(RESULT_OK,getIntent());
                    finish();
                    break;
                case -3:
                    actHelper.dismissProgressDialog();
                    MsgUtils.showMDMessage(context, (String) msg.obj);
                    break;
                case 4:
                    fkfs = fkfss.get((Integer) msg.obj).getId();
                    break;
                case 13:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog sxlxdialog = new ChoiceTypeDialog(context,handler,-13);
                    sxlxdialog.showDialog("请选择事项类型",isbjxz?bjsxlx:zbsxlx,sxlxs);
                    break;
                case -13:
                    sxlxid = sxlxs.get((Integer) msg.obj).getId();
                    break;
                case 14:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog sjlydialog = new ChoiceTypeDialog(context,handler,-14);
                    sjlydialog.showDialog("请选择涉及领域",isbjxz?bjsjly:zbsjly,sjlys);
                    break;
                case -14:
                    sjlyid = sjlys.get((Integer) msg.obj).getId();
                    break;
                case 16:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog slrdialog = new ChoiceTypeDialog(context,handler,-16);
                    slrdialog.showDialog("请选择受理人",zbslr,slrs);
                    break;
                case -16:
                    umid = slrs.get((Integer) msg.obj).getId();
                    break;
                case 15:
                    dismissProgressDialog();
                    int index = msg.arg1;
                    String messge = (String) msg.obj;
                    hfAdapter.setHf(index,messge);
                    break;
                case -15:
                    dismissProgressDialog();
                    MsgUtils.showMDMessage(context, (String) msg.obj);
                    break;
            }
        }
    };
}
