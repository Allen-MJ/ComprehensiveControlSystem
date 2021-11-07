package cn.lyj.thepublic.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.AllenBaseActivity;
import allen.frame.MultiImageSelector;
import allen.frame.entry.File;
import allen.frame.entry.Response;
import allen.frame.entry.UploadFile;
import allen.frame.net.Body;
import allen.frame.net.Callback;
import allen.frame.net.DataHttp;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.tools.StringUtils;
import allen.frame.tools.UploadProgressDialog;
import allen.frame.widget.CircleImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;
import cn.lyj.thepublic.main.TipoffActivity;

public class UserInfoActivity extends AllenBaseActivity {
    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R2.id.user_name)
    AppCompatEditText userName;
    @BindView(R2.id.user_phone)
    AppCompatEditText userPhone;
    @BindView(R2.id.user_sex)
    AppCompatTextView userSex;
    @BindView(R2.id.user_level)
    AppCompatTextView userLevel;
    @BindView(R2.id.user_email)
    AppCompatEditText userEmail;
    @BindView(R2.id.user_address)
    AppCompatEditText userAddress;
    @BindView(R2.id.ok_bt)
    AppCompatButton okBt;

    private SharedPreferences shared;
    private UploadProgressDialog dialog;
    private Map<String,Boolean> keys;
    private UploadFile uploadFile;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        setToolbarTitle(toolbar, getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shared = actHelper.getSharedPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(context).load(shared.getString(Constants.UserPhoto, ""))
                .placeholder(R.mipmap.ic_degault_photo)
                .error(R.mipmap.ic_degault_photo).into(userPhoto);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        userName.setText(shared.getString(Constants.UserName, ""));
        userPhone.setText(shared.getString(Constants.UserPhone, ""));
        dialog = new UploadProgressDialog();
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==2){
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
                    keys.put(file.getName(),true);
                    upload(file);
                }
            }
        }
    }


    private String name,phone,email,sex,address;
    private void submit(){
        name = userName.getText().toString().trim();
        phone = userPhone.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        address = userAddress.getText().toString().trim();
        sex = userSex.getText().toString().trim();
        if(StringUtils.empty(name)){
            MsgUtils.showMDMessage(context,"请输入姓名!");
            return;
        }
        if(StringUtils.empty(phone)){
            MsgUtils.showMDMessage(context,"请输入电话!");
            return;
        }
        if(StringUtils.empty(email)){
            MsgUtils.showMDMessage(context,"请输入邮箱!");
            return;
        }
        if(StringUtils.empty(address)){
            MsgUtils.showMDMessage(context,"请输入地址!");
            return;
        }
        if(StringUtils.empty(sex)){
            MsgUtils.showMDMessage(context,"请选择性别!");
            return;
        }
        String[] array={"nickName",userName.getText().toString(),"email",userEmail.getText().toString(),"phone",userPhone.getText().toString(),"gender",userSex.getText().toString()
        ,"avatarPath",uploadFile.getRelativePath(),"address",userAddress.getText().toString()};
        Body body=new Body();
        String json=body.postJson(array);
        Https.with(this).url(API._updateUserInfo).addJsons(json).post().enqueue(new Callback() {
            @Override
            public void success(Object data) {
                dismissProgressDialog();
                MsgUtils.showShortToast(context,"提交成功!");
                finish();
            }

            @Override
            public void fail(Response response) {
                dismissProgressDialog();
                MsgUtils.showMDMessage(context,response.getMsg());
            }
        });

    }

    private void upload(final File file){
        Https.with(this).url(API.upload).file(file).upload().enqueue(new Callback<UploadFile>() {

            @Override
            public void success(UploadFile data) {
//                map.put(file.getName(),data);
                Logger.e("success","success");
                uploadFile=data;
                Glide.with(context).load(Constants.url+data.getRelativePath())
                        .placeholder(R.mipmap.ic_degault_photo)
                        .error(R.mipmap.ic_degault_photo).into(userPhoto);

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
    @OnClick({R2.id.user_photo, R2.id.user_sex, R2.id.user_level, R2.id.ok_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.user_photo) {
            requestRunPermisssion(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    ,10,new PermissionListener(){
                        @Override
                        public void onGranted(int requestCode) {
                            MultiImageSelector.create()
                                    .single().showCamera(true)
                                    .start(UserInfoActivity.this,2);
                        }

                        @Override
                        public void onDenied(List<String> deniedPermission) {
                            MsgUtils.showShortToast(context,"上传需要开通权限!");
                        }
                    });

        } else if (id == R.id.user_sex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final String items[] = {"男", "女"};
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //1.把选中的条目取出来
                    String item = items[which];
                    userSex.setText(item);
                    //2.然后把对话框关闭
                    dialog.dismiss();
                }
            });
            builder.show();
        } else if (id == R.id.ok_bt) {
            submit();
        }
    }
}
