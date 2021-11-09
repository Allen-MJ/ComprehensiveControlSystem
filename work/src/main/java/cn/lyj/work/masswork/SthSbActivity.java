package cn.lyj.work.masswork;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.AllenBaseActivity;
import allen.frame.MultiImageSelector;
import allen.frame.entry.Type;
import allen.frame.tools.CheckUtils;
import allen.frame.tools.ChoiceTypeDialog;
import allen.frame.tools.Constants;
import allen.frame.tools.ImageUtils;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import cn.lyj.work.R;
import cn.lyj.work.adapter.ImageAdapter;

public class SthSbActivity extends AllenBaseActivity {

    private SharedPreferences shared;
    private Toolbar bar;
    private AppCompatTextView sldw, slr, fyqd, rklx, sxlx, sjly, zjdw, xbdw, dtzb, zbslr;
    private AppCompatEditText fyrae, sfzae, phoneae, jtdzae, fysxae, dbldae, blsxae, blyjae;
    private AppCompatCheckBox isdbac, isxbac;
    private RecyclerView recyclerview_file;
    private RadioGroup sexqg, jjcdqg;
    private String uid, ucode, utype;
    private String slrid, fyqdid, rklxid, sxlxid, sjlyid;
    private String fyr = "", sfz = "", phone = "", jtdz = "", fysx, dbld, mapaddress = "106.206036,29.573135";
    private String sex = "1", jjcd = "0", isDB = "0", isxb = "0";
    private String zbcode, xbcode, blsx, blyj, umid="";
    private ImageAdapter adapter;
    private ArrayList<String> list;
    private Map<String, Boolean> booleanMap;
    private String sjid = "";

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_sth_sb;
    }

    @Override
    protected void initBar() {
        bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        shared = actHelper.getSharedPreferences();
//        uid = shared.getString(Constants.USER_ID, "");
//        ucode = shared.getString(Constants.USER_UNITCODE, "");
//        utype = shared.getString(Constants.USER_UNITTYPE, "");
        sldw = findViewById(R.id.sl_dw);
        slr = findViewById(R.id.sl_slr);
        fyqd = findViewById(R.id.fy_fyqd);
        rklx = findViewById(R.id.fy_rklx);
        sxlx = findViewById(R.id.fy_sxlx);
        sjly = findViewById(R.id.fy_sjly);
        fyrae = findViewById(R.id.fyr_name);
        sfzae = findViewById(R.id.fyr_no);
        phoneae = findViewById(R.id.fyr_phone);
        dtzb = findViewById(R.id.fy_dtzb);
//        dtzb.setText(shared.getString(Constants.User_LocalPoint, ""));
        jtdzae = findViewById(R.id.fyr_familyaddress);
        fysxae = findViewById(R.id.fy_fysx);
        sexqg = findViewById(R.id.fyr_sex);
        jjcdqg = findViewById(R.id.fy_jjcd);
        isdbac = findViewById(R.id.fy_lddb);
        dbldae = findViewById(R.id.ld_name);
        zjdw = findViewById(R.id.zb_dwname);
        zbslr = findViewById(R.id.zb_slrname);
        recyclerview_file = findViewById(R.id.recyclerview_file);
//        findViewById(R.id.zb_slr_layout).setVisibility(View.);
        findViewById(R.id.zb_lxly_lay).setVisibility(View.GONE);
        blsxae = findViewById(R.id.zb_blsx);
        blyjae = findViewById(R.id.zb_blyj);
        isxbac = findViewById(R.id.zb_isxb);
        xbdw = findViewById(R.id.zb_sbdw_names);
        sexqg.check(R.id.sex_male);
        jjcdqg.check(R.id.jjcd_yb);
//        sldw.setText(shared.getString(Constants.USER_UNITNAME, ""));
        booleanMap = new HashMap<>();
        if (!utype.equals("1")) {
            zbcode = "101";
            zjdw.setText("璧泉街道");
        }
        initAdapter();
    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new ImageAdapter();
        GridLayoutManager manager = new GridLayoutManager(context, 4);
        recyclerview_file.setLayoutManager(manager);
        recyclerview_file.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                zbcode = data.getStringExtra("codes");
                zjdw.setText(data.getStringExtra("names"));
                if (utype.equals("1")) {
                    findViewById(R.id.zb_xb_layout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.zb_xb_layout).setVisibility(View.GONE);
                }
            } else if (requestCode == 2) {
                xbcode = data.getStringExtra("codes");
                xbdw.setText(data.getStringExtra("names"));
            } else if (requestCode == 3) {
                dtzb.setText(data.getStringExtra("point"));
            } else if (requestCode == 11) {
                ArrayList<String> mlist = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                list = mlist;
                adapter.setList(list);
            }
        }
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgUtils.showMDMessage(context, "你还未提交数据,是否退出？"
                        , "退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        });
        sexqg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.jjcd_jj) {
                    jjcd = "1";
                } else {
                    jjcd = "0";
                }
            }
        });
        jjcdqg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sex_male) {
                    sex = "1";
                } else {
                    sex = "0";
                }
            }
        });
        isdbac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isDB = "1";
                    findViewById(R.id.ld_name_lay).setVisibility(View.VISIBLE);
                } else {
                    isDB = "0";
                    dbldae.setText("");
                    findViewById(R.id.ld_name_lay).setVisibility(View.GONE);
                }
            }
        });
        isxbac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isxb = "1";
                    findViewById(R.id.xb_layout).setVisibility(View.VISIBLE);
                } else {
                    isxb = "0";
                    xbdw.setText("");
                    xbcode = "";
                    findViewById(R.id.xb_layout).setVisibility(View.GONE);
                }
            }
        });
        slr.setOnClickListener(l);
        fyqd.setOnClickListener(l);
        rklx.setOnClickListener(l);
        sxlx.setOnClickListener(l);
        sjly.setOnClickListener(l);
        zjdw.setOnClickListener(l);
        zbslr.setOnClickListener(l);
        xbdw.setOnClickListener(l);
        findViewById(R.id.fy_map_go).setOnClickListener(l);
        findViewById(R.id.fy_map_delete).setOnClickListener(l);
        findViewById(R.id.ok_bt).setOnClickListener(l);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void deleteClick(View v, int position) {

            }

            @Override
            public void addClick(View v) {
                MultiImageSelector.create().count(6)
                        .origin(adapter.getImages())
                        .showCamera(true)
                        .start(SthSbActivity.this, 11);
            }
        });
    }

    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.sl_slr) {
                getSlrList();
            } else if (id == R.id.fy_fyqd) {
                getFyqdList();
            } else if (id == R.id.fy_rklx) {
                getRklxList();
            } else if (id == R.id.fy_sxlx) {
                getSxlxList();
            } else if (id == R.id.fy_sjly) {
                getSjlyList();
            } else if (id == R.id.zb_dwname) {
                if (utype.equals("1")) {
//                    startActivityForResult(new Intent(context, ChoiceUnitsActivity.class).putExtra("more", false), 1);
                }
            } else if (id == R.id.zb_slrname) {
                getZbSlrList();
            } else if (id == R.id.zb_sbdw_names) {
                if (utype.equals("1")) {
//                    startActivityForResult(new Intent(context, ChoiceUnitsActivity.class).putExtra("more", true).putExtra("ucodes", xbcode), 2);
                }
            } else if (id == R.id.ok_bt) {
                if (checkIsOk()) {
                    MsgUtils.showMDMessage(context, "确认提交受理事项?", "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                                showProgressDialog("");

                            addSxSl();
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
            } else if (id == R.id.fy_map_go) {
//                startActivityForResult(new Intent(context, MapActivity.class), 3);
            } else if (id == R.id.fy_map_delete) {
                dtzb.setText("");
            }
        }
    };

    private boolean checkIsOk() {
        fyr = fyrae.getText().toString().trim();
        sfz = sfzae.getText().toString().trim();
        phone = phoneae.getText().toString().trim();
        jtdz = jtdzae.getText().toString().trim();
        fysx = fysxae.getText().toString().trim();
        dbld = dbldae.getText().toString().trim();
        blsx = blsxae.getText().toString().trim();
        blyj = blyjae.getText().toString().trim();
        mapaddress = dtzb.getText().toString().trim();
        if (StringUtils.empty(slrid)) {
            MsgUtils.showMDMessage(context, "请选择受理人!");
            return false;
        }
        if(StringUtils.empty(fyr)){
            MsgUtils.showMDMessage(context,"请输入反映人姓名!");
            return false;
        }
//        if(StringUtils.empty(sfz)){
//            MsgUtils.showMDMessage(context,"请输入反映人身份证号码!");
//            return false;
//        }else if(!CheckUtils.IDIsOk(sfz)){
//            MsgUtils.showMDMessage(context,"请输入正确的反映人身份证号码!");
//            return false;
//        }
        if (StringUtils.notEmpty(sfz)) {
            if (!CheckUtils.IDIsOk(sfz)) {
                MsgUtils.showMDMessage(context, "请输入正确的反映人身份证号码!");
                return false;
            }
        }
        if (StringUtils.empty(phone)) {
            MsgUtils.showMDMessage(context, "请输入反映人手机号!");
            return false;
        } else if (!CheckUtils.phoneIsOk(phone)) {
            MsgUtils.showMDMessage(context, "请输入正确的反映人手机号!");
            return false;
        }
        if (StringUtils.empty(fyqdid)) {
            MsgUtils.showMDMessage(context, "请选择反映渠道!");
            return false;
        }
        if (StringUtils.empty(rklxid)) {
            MsgUtils.showMDMessage(context, "请选择人口类型!");
            return false;
        }
        if (StringUtils.empty(sxlxid)) {
            MsgUtils.showMDMessage(context, "请选择事项类型!");
            return false;
        }
        if (StringUtils.empty(sjlyid)) {
            MsgUtils.showMDMessage(context, "请选择涉及领域!");
            return false;
        }
        if (StringUtils.empty(fysx)) {
            MsgUtils.showMDMessage(context, "请输入反映事项!");
            return false;
        }
        if (isDB.equals("1")) {
            if (StringUtils.empty(dbld)) {
                MsgUtils.showMDMessage(context, "请输入督办领导!");
                return false;
            }
        } else {
            dbld = "";
        }
        if (StringUtils.empty(zbcode)) {
            MsgUtils.showMDMessage(context, "请选择转交单位!");
            return false;
        }
        if (StringUtils.empty(blsx)) {
            MsgUtils.showMDMessage(context, "请输入办理时限!");
            return false;
        }
        if (isxb.equals("1")) {
            if (StringUtils.empty(xbcode)) {
                MsgUtils.showMDMessage(context, "请选择转交单位!");
                return false;
            }
        }
        if (StringUtils.empty(blyj)) {
            MsgUtils.showMDMessage(context, "请输入转办意见!");
            return false;
        }
        return true;
    }

    private List<Type> slrs;

    private void getSlrList() {
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                slrs = AppDataHelper.init().getSlrList(ucode);
//                handler.sendEmptyMessage(10);
            }
        }).start();
    }

    private void upload(Bitmap file, String fileName) {
//        boolean isok=AppDataHelper.init().SJFileUp(ImageUtils.bitmap2base64(file), fileName, sjid, uid, ucode);
//        booleanMap.put(fileName,isok);
    }

    private List<Type> zbslrs;

    private void getZbSlrList() {
        if (StringUtils.empty(zbcode)) {
            MsgUtils.showMDMessage(context, "请选择转交单位!");
            return;
        }
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                zbslrs = AppDataHelper.init().getSlrList(zbcode);
//                handler.sendEmptyMessage(15);
            }
        }).start();
    }

    private List<Type> fyqds;

    private void getFyqdList() {
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                fyqds = AppDataHelper.init().getTypeList("11");
//                handler.sendEmptyMessage(11);
            }
        }).start();
    }

    private List<Type> rklxs;

    private void getRklxList() {
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                rklxs = new ArrayList<>();
                rklxs.add(new Type("0", "常住人口"));
                rklxs.add(new Type("1", "流动人口"));
//                rklxs = AppDataHelper.init().getTypeList("14");
                handler.sendEmptyMessage(12);
            }
        }).start();
    }

    private List<Type> sxlxs;

    private void getSxlxList() {
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

    private void getSjlyList() {
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                sjlys = AppDataHelper.init().getTypeList("16");
//                handler.sendEmptyMessage(14);
            }
        }).start();
    }

    private void addSxSl() {
        actHelper.showProgressDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
//                AppDataHelper.init().addSxSl(handler, ucode, slrid, fyr, sfz, phone, sex, fyqdid, rklxid, jtdz, jjcd, sxlxid, sjlyid, isDB, dbld, fysx, mapaddress,
//                        zbcode, blsx, isxb, xbcode, blyj, uid, umid, sjid);
            }
        }).start();
    }

    private void uploadFiles() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    File file = new File(list.get(i));
                    Bitmap bitmap = ImageUtils.getimage(list.get(i));
                    if (!booleanMap.containsKey(file.getName()) || !booleanMap.get(file.getName())) {
                        upload(bitmap, file.getName());
                    }
                }
                boolean isOk = true;
                int failcount = 0;
                for (String path : list) {
                    File file = new File(path);
                    boolean itemOk = booleanMap.get(file.getName());
                    isOk = isOk && itemOk;
                    if (itemOk) {
                        failcount++;
                    }
                }
                Message msg = new Message();
                msg.what = -3;
                msg.obj = isOk;
                msg.arg1 = failcount;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    int len = list == null ? 0 : list.size();
                    if (len == 0) {
                        actHelper.dismissProgressDialog();
                        MsgUtils.showShortToast(context, (String) msg.obj);
                        finish();
                    } else {
                        sjid = (String) msg.obj;
                        uploadFiles();

                    }


                    break;
                case -1:
                    actHelper.dismissProgressDialog();
                    MsgUtils.showMDMessage(context, (String) msg.obj);
                    break;
                case -3:
                    boolean isok = (boolean) msg.obj;
                    int failcount=msg.arg1;
                    if (isok) {
                        actHelper.dismissProgressDialog();
                        MsgUtils.showShortToast(context, "上传成功！");
                        finish();
                    } else {
                        actHelper.dismissProgressDialog();
                        MsgUtils.showMDMessage(context, failcount + "个附件上传失败,请重新提交!");
                    }
                    break;
                case 10:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog slrdialog = new ChoiceTypeDialog(context, handler, -10);
                    slrdialog.showDialog("请选择受理人", slr, slrs);
                    break;
                case -10:
                    slrid = slrs.get((Integer) msg.obj).getId();
                    break;
                case 11:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog fyqddialog = new ChoiceTypeDialog(context, handler, -11);
                    fyqddialog.showDialog("请选择反映渠道", fyqd, fyqds);
                    break;
                case -11:
                    fyqdid = fyqds.get((Integer) msg.obj).getId();
                    break;
                case 12:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog rklxdialog = new ChoiceTypeDialog(context, handler, -12);
                    rklxdialog.showDialog("请选择人口类型", rklx, rklxs);
                    break;
                case -12:
                    rklxid = rklxs.get((Integer) msg.obj).getId();
                    break;
                case 13:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog sxlxdialog = new ChoiceTypeDialog(context, handler, -13);
                    sxlxdialog.showDialog("请选择事项类型", sxlx, sxlxs);
                    break;
                case -13:
                    sxlxid = sxlxs.get((Integer) msg.obj).getId();
                    break;
                case 14:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog sjlydialog = new ChoiceTypeDialog(context, handler, -14);
                    sjlydialog.showDialog("请选择涉及领域", sjly, sjlys);
                    break;
                case -14:
                    sjlyid = sjlys.get((Integer) msg.obj).getId();
                    break;
                case 15:
                    actHelper.dismissProgressDialog();
                    ChoiceTypeDialog zbslrdialog = new ChoiceTypeDialog(context, handler, -15);
                    zbslrdialog.showDialog("请选择受理人", zbslr, zbslrs);
                    break;
                case -15:
                    umid = zbslrs.get((Integer) msg.obj).getId();
                    break;
            }
        }
    };
}
