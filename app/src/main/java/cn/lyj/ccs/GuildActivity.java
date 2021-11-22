package cn.lyj.ccs;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import allen.frame.AllenIMBaseActivity;
import allen.frame.AllenManager;
import allen.frame.entry.BGALocalImageSize;
import allen.frame.entry.Response;
import allen.frame.entry.Version;
import allen.frame.net.BaseApi;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.FileIntent;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.tools.StringUtils;
import allen.frame.widget.BGABanner;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import cn.lyj.thepublic.LoginActivity;

public class GuildActivity extends AllenIMBaseActivity {
    @BindView(R.id.guild)
    BGABanner guild;
    @BindView(R.id.tv_guide_skip)
    AppCompatTextView tvGuideSkip;
    @BindView(R.id.btn_guide_enter)
    AppCompatButton btnGuideEnter;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.guild_layout;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        processLogic();
        requestRunPermisssion(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10, new PermissionListener() {
            @Override
            public void onGranted(int requestCode) {
                version();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                MsgUtils.exitNotOutMDMessage(context,"请开通文件读写权限!");
            }
        });
    }

    @Override
    protected void addEvent() {
        guild.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });
    }

    private void processLogic() {
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        guild.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.guild_1);
    }

    private void version(){
        showProgressDialog("正在加载,请稍等...");
        Https.with(this).url(BaseApi.Version).addParam("type",3).get()
                .enqueue(new Callback<Version>() {
                    @Override
                    public void success(Version data) {
                        dismissProgressDialog();
                        if(data!=null){
                            if(AllenManager.getInstance().isNewVersion(data.getAppVersion())){
                                download(data.getAppUrl());
                            }
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        dismissProgressDialog();
                        MsgUtils.exitNotOutMDMessage(context,response.getMsg());
                    }
                });
    }
    private void download(String url){
        ProgressDialog dialog = new ProgressDialog(context, allen.frame.R.style.Base_V21_Theme_AppCompat_Light_Dialog);
        dialog.setTitle("版本更新");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
        Https.with(this).url(url).download()
                .enqueue(new Callback<File>() {
                    @Override
                    public void success(File data) {
                        dialog.dismiss();
                        FileIntent.installApk(context,data);
                    }

                    @Override
                    public void onProgress(long total, long current) {
                        dialog.setProgressNumberFormat(String.format("%s/%s", StringUtils.formatFileSize(current),StringUtils.formatFileSize(total)));
                    }

                    @Override
                    public void fail(Response response) {
                        dialog.dismiss();
                        MsgUtils.exitNotOutMDMessage(context,response.getMsg());
                    }
                });
    }
}
