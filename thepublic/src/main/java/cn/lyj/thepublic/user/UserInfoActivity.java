package cn.lyj.thepublic.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

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
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.tools.StringUtils;
import allen.frame.tools.UploadProgressDialog;
import allen.frame.widget.CircleImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;

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
        setToolbarTitle(toolbar, "????????????",true);
        shared = actHelper.getSharedPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(context).load(Constants.url+shared.getString(Constants.UserPhoto, ""))
                .placeholder(R.mipmap.ic_degault_photo)
                .error(R.mipmap.ic_degault_photo).into(userPhoto);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        userName.setText(shared.getString(Constants.UserNickName, ""));
        userPhone.setText(shared.getString(Constants.UserPhone, ""));
        userAddress.setText(shared.getString(Constants.UserAddress,""));
        userSex.setText(shared.getString(Constants.UserGender,""));
        userEmail.setText(shared.getString(Constants.UserEmail,""));
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
                MsgUtils.showShortToast(context,"????????????!");
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
                    file.setType(0);//??????
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
            MsgUtils.showMDMessage(context,"???????????????!");
            return;
        }
        if(StringUtils.empty(phone)){
            MsgUtils.showMDMessage(context,"???????????????!");
            return;
        }
        if(StringUtils.empty(email)){
            MsgUtils.showMDMessage(context,"???????????????!");
            return;
        }
        if(StringUtils.empty(address)){
            MsgUtils.showMDMessage(context,"???????????????!");
            return;
        }
        if(StringUtils.empty(sex)){
            MsgUtils.showMDMessage(context,"???????????????!");
            return;
        }
        String[] array={"nickName",userName.getText().toString(),"email",userEmail.getText().toString(),
                "phone",userPhone.getText().toString(),
                "gender",userSex.getText().toString(),
                "avatarPath",uploadFile==null?shared.getString(Constants.UserPhoto, ""):uploadFile.getRelativePath(),
                "address",userAddress.getText().toString()};
        Body body=new Body();
        String json=body.postJson(array);
        Https.with(this).url(API._updateUserInfo).addJsons(json).post().enqueue(new Callback() {
            @Override
            public void success(Object data) {
                dismissProgressDialog();
                MsgUtils.showShortToast(context,"????????????!");
                shared.edit().putString(Constants.UserNickName,userName.getText().toString())
                        .putString(Constants.UserEmail,userEmail.getText().toString())
                        .putString(Constants.UserGender,userSex.getText().toString())
                        .putString(Constants.UserPhoto,uploadFile==null?shared.getString(Constants.UserPhoto, ""):uploadFile.getRelativePath())
                        .putString(Constants.UserAddress,userAddress.getText().toString())
                        .putString(Constants.UserPhone,userPhone.getText().toString()).apply();
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
                            MsgUtils.showShortToast(context,"????????????????????????!");
                        }
                    });

        } else if (id == R.id.user_sex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final String items[] = {"???", "???"};
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //1.???????????????????????????
                    String item = items[which];
                    userSex.setText(item);
                    //2.????????????????????????
                    dialog.dismiss();
                }
            });
            builder.show();
        } else if (id == R.id.ok_bt) {
            submit();
        }
    }
}
