package cn.lyjj.thepublic;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.AllenBaseActivity;
import allen.frame.MultiImageSelector;
import allen.frame.adapter.AllenFileChoiceAdapter;
import allen.frame.entry.File;
import allen.frame.entry.Response;
import allen.frame.entry.UploadFile;
import allen.frame.net.Callback;
import allen.frame.net.Https;
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

public class SmartTipActivity extends AllenBaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tip_dw)
    AppCompatTextView tipDw;
    @BindView(R2.id.tip_content)
    AppCompatEditText tipContent;
    @BindView(R2.id.tip_file)
    RecyclerView tipFile;
    @BindView(R2.id.tip_bt)
    AppCompatButton tipBt;

    private AllenFileChoiceAdapter adapter;
    private ArrayList<File> files;
    private UploadProgressDialog dialog;
    private Map<String, UploadFile> map;
    private Map<String,Boolean> keys;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.tipoff_smart_layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==2){
                files = new ArrayList<>();
                assert data != null;
                keys = new HashMap<>();
                ArrayList<String> paths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                dialog.init(context);
                for(String path:paths){
                    File file = new File();
                    file.setName(StringUtils.getFileNameByPath(path));
                    file.setPath(path);
                    file.setType(0);//图片
                    file.setSuffix(FileIntent.getMIMEType(file.getFile()));
                    files.add(file);
                    keys.put(file.getName(),true);
                    upload(file);
                }
                adapter.setData(files);
            }
        }
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"快速上报",true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        files = new ArrayList<>();
        map = new HashMap<>();
        keys = new HashMap<>();
        GridLayoutManager manager = new GridLayoutManager(context,4);
        tipFile.setLayoutManager(manager);
        adapter = new AllenFileChoiceAdapter();
        tipFile.setAdapter(adapter);
        dialog = new UploadProgressDialog();
    }

    @Override
    protected void addEvent() {
        adapter.setOnItemClickListener(new AllenFileChoiceAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position, File file) {

            }

            @Override
            public void onItemDelete(View v, int position, File file) {
                adapter.delete(file);
            }

            @Override
            public void onAddClick() {
                requestRunPermisssion(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        ,10,new PermissionListener(){
                            @Override
                            public void onGranted(int requestCode) {
                                MultiImageSelector.create()
                                        .multi().showCamera(true).count(6).origin(adapter.getPaths())
                                        .start(SmartTipActivity.this,2);
                            }

                            @Override
                            public void onDenied(List<String> deniedPermission) {
                                MsgUtils.showShortToast(context,"上传需要开通权限!");
                            }
                        });
            }
        });
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
                MsgUtils.showShortToast(context,"上传成功!");
            }
        });
    }

    @OnClick({R2.id.tip_dw, R2.id.tip_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.tip_dw){

        }else if(id==R.id.tip_bt){
            addTipOff();
        }
        view.setEnabled(true);
    }

    private void upload(final File file){
        Https.with(this).url(TipApi.upload).file(file).upload().enqueue(new Callback<UploadFile>() {

            @Override
            public void success(UploadFile data) {
                map.put(file.getName(),data);
                Logger.e("success","success");
            }

            @Override
            public void onProgress(long total, long current) {
                Logger.e("progress",total+":"+current);
                dialog.changeProgress(file.getName(),total,current);
            }

            @Override
            public void fail(Response response) {
                Logger.e("fail","fail");
            }
        });
    }

    private String orgId,content;
    private void addTipOff(){
        content = tipContent.getText().toString().trim();
        if(StringUtils.empty(orgId)){
            MsgUtils.showMDMessage(context,"请选择受理单位!");
            return;
        }
        if(StringUtils.empty(content)){
            MsgUtils.showMDMessage(context,"请输入爆料内容!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        if(map.size()>0){
            for(Map.Entry<String,UploadFile> entry:map.entrySet()){
                if(keys.get(entry.getKey())){
                    sb.append(","+entry.getValue().getId());
                }
            }
            sb.delete(0,1);
        }
        showProgressDialog("正在提交爆料,请稍等...");
        Https.with(this).url(TipApi.SmartAdd).addParam("appealOrgId",orgId).addParam("content",content)
                .addParam("fileIds",sb.toString())
                .post()
                .enqueue(new Callback<Object>() {

                    @Override
                    public void success(Object data) {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context,"提交成功,等待处理!");
                        finish();
                    }

                    @Override
                    public void token() {
                        dismissProgressDialog();
                        MsgUtils.showShortToast(context,"账号登录过期,请重新登录!");
                        /*startActivityForResult(new Intent(context, LoginActivity.class).putExtra(Constants.Key_Token,true),11);*/
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
}
