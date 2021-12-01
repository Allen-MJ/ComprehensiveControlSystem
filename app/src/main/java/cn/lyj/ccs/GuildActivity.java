package cn.lyj.ccs;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionListener;
import allen.frame.tools.StringUtils;
import allen.frame.widget.BGABanner;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;

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
    }

    @Override
    protected void addEvent() {
        guild.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(context, TestActivity.class));
//                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });
    }

    private void processLogic() {
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        guild.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.guild_1);
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

    private void version(){
        actHelper.showProgressDialog("正在加载,请稍等...");
        Https https = Https.with(context).url(BaseApi.Version);
        if(Constants.version==0){
            https.addParam("type",3);
        }else if(Constants.version==1){
            https.addParam("type",1);
        }else{
            https.addParam("type",2);
        }
        https.get()
                .enqueue(new Callback<Version>() {
                    @Override
                    public void success(final Version data) {
                        actHelper.dismissProgressDialog();
                        if(data!=null&& AllenManager.getInstance().isNewVersion(data.getAppVersion())){
                            if(data.isAppCompel()){
                                download(Constants.url+data.getAppPath());
                            }else{
                                MsgUtils.showNotOutMDMessage(context, "新版本", data.getAppDesc(), "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        download(Constants.url+data.getAppPath());
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        actHelper.dismissProgressDialog();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
    private void download(String url){
        final ProgressDialog udialog = new ProgressDialog(context, allen.frame.R.style.Base_V21_Theme_AppCompat_Light_Dialog);
        udialog.setTitle("版本更新");
        udialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        udialog.setMax(100);
        udialog.setProgress(0);
        udialog.setCancelable(false);
        udialog.show();
        Https.with(context).url(url)
                .download(new Callback<File>() {
                    @Override
                    public void success(File data) {
                        udialog.dismiss();
                        FileIntent.installApk(context,data);
                    }

                    @Override
                    public void onProgress(long total, long current) {
                        udialog.setProgress((int) (100*current*total));
                        udialog.setProgressNumberFormat(String.format("%s/%s", StringUtils.formatFileSize(current),StringUtils.formatFileSize(total)));
                    }

                    @Override
                    public void fail(Response response) {
                        udialog.dismiss();
                        MsgUtils.showMDMessage(context,response.getMsg());
                    }
                });
    }
}
