package cn.lyjj.thepublic;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.AllenBaseActivity;
import allen.frame.AllenChoiceGridActivity;
import allen.frame.AllenChoiceUnitsActivity;
import allen.frame.AllenMapChoiceActivity;
import allen.frame.MultiImageSelector;
import allen.frame.adapter.AllenFileChoiceAdapter;
import allen.frame.adapter.AllenFileChoiceAdapter.OnItemClickListener;
import allen.frame.entry.File;
import allen.frame.entry.Response;
import allen.frame.entry.UploadFile;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.tools.StringUtils;
import allen.frame.tools.UploadProgressDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyjj.thepublic.api.TipApi;

public class TipoffActivity extends AllenBaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tip_dw)
    AppCompatTextView tipDw;
    @BindView(R2.id.tip_fyr)
    AppCompatEditText tipFyr;
    @BindView(R2.id.tip_sfz)
    AppCompatEditText tipSfz;
    @BindView(R2.id.tip_phone)
    AppCompatEditText tipPhone;
    @BindView(R2.id.tip_sex)
    RadioGroup tipSex;
    @BindView(R2.id.tip_grid)
    AppCompatTextView tipGrid;
    @BindView(R2.id.tip_address)
    AppCompatTextView tipAddress;
    @BindView(R2.id.tip_content)
    AppCompatEditText tipContent;
    @BindView(R2.id.tip_file)
    RecyclerView tipFile;
    @BindView(R2.id.tip_bt)
    AppCompatButton tipBt;
    @BindView(R2.id.tip_point)
    AppCompatTextView tipPoint;
    private AllenFileChoiceAdapter adapter;
    private ArrayList<File> files;
    private UploadProgressDialog dialog;
    private Map<String, UploadFile> map;
    private Map<String, Boolean> keys;
    private int type = 0;
    private String title, api;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.tipoff_layout;
    }

    @Override
    protected void initBar() {
        type = getIntent().getIntExtra(Constants.Key_1, 0);
        if (type == 0) {
            title = "爆料";
            api = TipApi.TipPublicAdd;
        } else if (type == 1) {
            title = "事件上报";
            api = TipApi.TipGridAdd;
        } else {
            title = "事件上报";
            api = TipApi.TipLeaderAdd;
        }
        setToolbarTitle(toolbar, title, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                files = new ArrayList<>();
                assert data != null;
                keys = new HashMap<>();
                ArrayList<String> paths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                dialog.init(context);
                for (String path : paths) {
                    File file = new File();
                    file.setName(StringUtils.getFileNameByPath(path));
                    file.setPath(path);
                    file.setType(0);//图片
                    file.setSuffix(FileIntent.getMIMEType(file.getFile()));
                    files.add(file);
                    keys.put(file.getName(), true);
                    upload(file);
                }
                adapter.setData(files);
            } else if (requestCode == 3) {
                orgId = data.getStringExtra(Constants.Key_1);
                tipDw.setText(data.getStringExtra(Constants.Key_2));
            } else if (requestCode == 4) {
                gid = data.getStringExtra(Constants.Key_1);
                tipGrid.setText(data.getStringExtra(Constants.Key_2));
            } else if (requestCode == 5) {
                PoiInfo info = data.getParcelableExtra(Constants.ObjectFirst);
                address = info.getAddress();
                tipAddress.setText(address);
                point = info.getLocation().longitude+","+info.getLocation().latitude;
                Logger.e("address",address+"||"+point);
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        files = new ArrayList<>();
        map = new HashMap<>();
        keys = new HashMap<>();
        tipPhone.setText(shared.getString(Constants.UserPhone, ""));
        GridLayoutManager manager = new GridLayoutManager(context, 4);
        tipFile.setLayoutManager(manager);
        adapter = new AllenFileChoiceAdapter();
        tipFile.setAdapter(adapter);
        dialog = new UploadProgressDialog();
    }

    @Override
    protected void addEvent() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, File file) {

            }

            @Override
            public void onItemDelete(View v, int position, File file) {
                adapter.delete(file);
            }

            @Override
            public void onAddClick() {
                requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , 10, new PermissionListener() {
                            @Override
                            public void onGranted(int requestCode) {
                                MultiImageSelector.create()
                                        .multi().showCamera(true).count(6).origin(adapter.getPaths())
                                        .start(TipoffActivity.this, 2);
                            }

                            @Override
                            public void onDenied(List<String> deniedPermission) {
                                MsgUtils.showShortToast(context, "上传需要开通权限!");
                            }
                        });
            }
        });
        tipSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sex_male) {
                    sex = "1";
                } else {
                    sex = "0";
                }
            }
        });
        if(shared.getString(Constants.UserGender,"").equals("女")){
            sex = "0";
            tipSex.check(R.id.sex_female);
        }else{
            sex = "1";
            tipSex.check(R.id.sex_male);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.setOnCompletListener(new UploadProgressDialog.OnCompletListener() {
            @Override
            public void onComplet(ProgressDialog dialog) {
                dialog.dismiss();
                MsgUtils.showShortToast(context, "上传成功!");
            }
        });
    }

    @OnClick({R2.id.tip_dw, R2.id.tip_grid, R2.id.tip_point, R2.id.tip_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if (id == R.id.tip_dw) {
            startActivityForResult(new Intent(context, AllenChoiceUnitsActivity.class), 3);
        } else if (id == R.id.tip_grid) {
            startActivityForResult(new Intent(context, AllenChoiceGridActivity.class), 4);
        } else if (id == R.id.tip_bt) {
            addTipOff();
        } else if(id == R.id.tip_point){
            requestRunPermisssion(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                    , 10, new PermissionListener() {
                        @Override
                        public void onGranted(int requestCode) {
                            startActivityForResult(new Intent(context, AllenMapChoiceActivity.class),5);
                        }

                        @Override
                        public void onDenied(List<String> deniedPermission) {
                            MsgUtils.showShortToast(context, "定位需要开通权限!");
                        }
                    });
        }
        view.setEnabled(true);
    }

    private String orgId, name, phone, idNumber, sex = "0", address, point, gid, content;

    private void addTipOff() {
        name = tipFyr.getText().toString().trim();
        phone = tipPhone.getText().toString().trim();
        idNumber = tipSfz.getText().toString().trim();
        address = tipAddress.getText().toString().trim();
        content = tipContent.getText().toString().trim();
        if (StringUtils.empty(orgId)) {
            MsgUtils.showMDMessage(context, "请选择受理单位!");
            return;
        }
        if (StringUtils.empty(name)) {
            MsgUtils.showMDMessage(context, "请输入反映人姓名!");
            return;
        }
        if (StringUtils.empty(idNumber)) {
            MsgUtils.showMDMessage(context, "请输入身份证号码!");
            return;
        }
        if (StringUtils.empty(address)) {
            MsgUtils.showMDMessage(context, "请先定位!");
            return;
        }
        if (StringUtils.empty(content)) {
            MsgUtils.showMDMessage(context, "请输入爆料内容!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (map.size() > 0) {
            for (Map.Entry<String, UploadFile> entry : map.entrySet()) {
                if (keys.get(entry.getKey())) {
                    sb.append("," + entry.getValue().getId());
                }
            }
            sb.delete(0, 1);
        }
        showProgressDialog("正在提交爆料,请稍等...");
        Https.with(this).url(api).addParam("appealOrgId", orgId).addParam("name", name).addParam("phone", phone).addParam("idNumber", idNumber)
                .addParam("sex", sex).addParam("point", point).addParam("address", address).addParam("gid", gid).addParam("content", content)
                .addParam("fileIds", sb.toString())
                .post()
                .enqueue(new Callback<Object>() {

                    @Override
                    public void success(Object data) {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context, "提交成功,等待处理!");
                        finish();
                    }

                    @Override
                    public void token() {
                        dismissProgressDialog();
                        actHelper.tokenErro2Login(TipoffActivity.this);
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context, response.getMsg());
                    }
                });
    }

    private void upload(final File file) {
        Https.with(this).url(TipApi.upload).file(file).upload().enqueue(new Callback<UploadFile>() {

            @Override
            public void success(UploadFile data) {
                map.put(file.getName(), data);
                Logger.e("success", "success");
            }

            @Override
            public void onProgress(long total, long current) {
                Logger.e("progress", total + ":" + current);
                dialog.changeProgress(file.getName(), total, current);
            }

            @Override
            public void fail(Response response) {
                Logger.e("fail", "fail");
            }
        });
    }

}
