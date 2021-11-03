package cn.lyj.thepublic.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import allen.frame.AllenBaseActivity;
import allen.frame.MultiImageSelector;
import allen.frame.adapter.AllenFileChoiceAdapter;
import allen.frame.entry.File;
import allen.frame.entry.Response;
import allen.frame.entry.UploadFile;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.FileUtils;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.LoginActivity;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;

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
    AppCompatEditText tipAddress;
    @BindView(R2.id.tip_content)
    AppCompatEditText tipContent;
    @BindView(R2.id.tip_file)
    RecyclerView tipFile;
    @BindView(R2.id.tip_bt)
    AppCompatButton tipBt;
    private AllenFileChoiceAdapter adapter;
    private ArrayList<File> files;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.public_tipoff_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"爆料",true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==2){
                files = new ArrayList<>();
                assert data != null;
                ArrayList<String> paths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                for(String path:paths){
                    File file = new File();
                    file.setName(StringUtils.getFileNameByPath(path));
                    file.setPath(path);
                    file.setType(0);//图片
                    file.setSuffix(FileIntent.getMIMEType(file.getFile()));
                    files.add(file);
                    upload(file);
                }
                adapter.setData(files);
            }
        }
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        files = new ArrayList<>();
        tipPhone.setText(shared.getString(Constants.UserPhone,""));
        GridLayoutManager manager = new GridLayoutManager(context,4);
        tipFile.setLayoutManager(manager);
        adapter = new AllenFileChoiceAdapter();
        tipFile.setAdapter(adapter);
    }

    @Override
    protected void addEvent() {
        adapter.setOnItemClickListener(new AllenFileChoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, File file) {

            }

            @Override
            public void onItemDelete(View v, int position, File file) {
                adapter.delete(file);
            }

            @Override
            public void onAddClick() {
                MultiImageSelector.create()
                        .multi().showCamera(true).count(6).origin(adapter.getPaths())
                        .start(TipoffActivity.this,2);
            }
        });
        tipSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.sex_male){

                }else{

                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R2.id.tip_dw, R2.id.tip_grid, R2.id.tip_bt})
    public void onViewClicked(View view) {
        view.setEnabled(false);
        int id = view.getId();
        if(id==R.id.tip_dw){

        }else if(id==R.id.tip_grid){

        }else if(id==R.id.tip_bt){
            addTipOff();
        }
        view.setEnabled(true);
    }

    private String orgId,name,phone,idNumber,sex,address,gid,content;
    private void addTipOff(){
        orgId = "";
        name = tipFyr.getText().toString().trim();
        phone = tipPhone.getText().toString().trim();
        idNumber = tipSfz.getText().toString().trim();
        address = tipAddress.getText().toString().trim();
        gid = "";
        content = tipContent.getText().toString().trim();
        if(StringUtils.empty(orgId)){
            MsgUtils.showMDMessage(context,"请选择受理单位!");
            return;
        }
        if(StringUtils.empty(name)){
            MsgUtils.showMDMessage(context,"请输入反映人姓名!");
            return;
        }
        if(StringUtils.empty(idNumber)){
            MsgUtils.showMDMessage(context,"请输入身份证号码!");
            return;
        }
        if(StringUtils.empty(address)){
            MsgUtils.showMDMessage(context,"请先定位!");
            return;
        }
        if(StringUtils.empty(content)){
            MsgUtils.showMDMessage(context,"请输入爆料内容!");
            return;
        }
        showProgressDialog("正在提交爆料,请稍等...");
        Https.with(this).addParam("appealOrgId",orgId).addParam("name",name).addParam("phone",phone).addParam("idNumber",idNumber)
                .addParam("sex",sex).addParam("point","").addParam("address",address).addParam("gid",gid).addParam("content",content)
                .addParam("fileIds","")
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
                        startActivityForResult(new Intent(context, LoginActivity.class).putExtra(Constants.Key_Token,true),11);
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }

    private void upload(File file){
        Https.with(this).url(API.upload).file(file).upload().enqueue(new Callback<UploadFile>() {

            @Override
            public void success(UploadFile data) {
                Logger.e("success","success");
            }

            @Override
            public void onProgress(long total, long current) {
                Logger.e("progress",total+":"+current);
            }

            @Override
            public void fail(Response response) {
                Logger.e("fail","fail");
            }
        });
    }
}
