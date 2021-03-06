package cn.lyj.thepublic.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import java.io.File;

import allen.frame.AllenManager;
import allen.frame.BaseFragment;
import allen.frame.entry.Response;
import allen.frame.entry.Version;
import allen.frame.net.BaseApi;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.FileIntent;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.CircleImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lyj.thepublic.LoginActivity;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;

public class OwnFragment extends BaseFragment {

    @BindView(R2.id.user_name)
    AppCompatTextView userName;
    @BindView(R2.id.user_dw)
    AppCompatTextView userDw;
    @BindView(R2.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R2.id.info_layout)
    LinearLayoutCompat infoLayout;
    @BindView(R2.id.user_gz)
    AppCompatTextView userGz;
    @BindView(R2.id.user_zan)
    AppCompatTextView userZan;
    @BindView(R2.id.user_notice_number)
    AppCompatTextView userNoticeNumber;
    @BindView(R2.id.user_pl)
    LinearLayoutCompat userPl;
    @BindView(R2.id.user_fk)
    LinearLayoutCompat userFk;
    @BindView(R2.id.user_update)
    AppCompatTextView userUpdate;
    @BindView(R2.id.exit_btn)
    AppCompatButton exitBtn;

    private SharedPreferences shared;
    private int relateState = -1;

    public static OwnFragment init() {
        OwnFragment fragment = new OwnFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_own;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shared = actHelper.getSharedPreferences();
        initUI(view);
        addEvent(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        userName.setText(shared.getString(Constants.UserNickName, ""));
        userDw.setText(shared.getString(Constants.UserAddress,""));
        Glide.with(this)
                .load(Constants.url+shared.getString(Constants.UserPhoto,""))
                .error(R.drawable.default_photo)
                .into(userPhoto);
    }

    private void initUI(View view) {
        userName.setText(shared.getString(Constants.UserNickName, ""));
        userDw.setText(shared.getString(Constants.UserAddress,""));
        userNoticeNumber.setText("");
        String number = userNoticeNumber.getText().toString().trim();
        userNoticeNumber.setVisibility(StringUtils.empty(number) ? View.INVISIBLE : View.VISIBLE);
    }

    private void addEvent(View view) {

    }

    private void logout() {
        Https.with(getActivity()).url(API._logout)
                .get()
                .enqueue(new Callback() {
                    @Override
                    public void success(Object data) {
                        MsgUtils.showLongToast(getContext(), "????????????!");
                        AllenManager.getInstance().back2Activity(LoginActivity.class);
                    }

                    @Override
                    public void fail(Response response) {
                        MsgUtils.showLongToast(getContext(), response.getMsg());
                    }
                });
    }

    @OnClick({R2.id.user_name, R2.id.user_dw, R2.id.user_photo, R2.id.info_layout, R2.id.user_gz, R2.id.user_zan, R2.id.user_notice_number,
            R2.id.user_pl, R2.id.user_fk, R2.id.user_update, R2.id.exit_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.user_name) {
            startActivity(new Intent(getContext(), UserInfoActivity.class));
        } else if (id == R.id.user_dw) {
            startActivity(new Intent(getContext(), UserInfoActivity.class));
        } else if (id == R.id.user_photo) {
            startActivity(new Intent(getContext(), UserInfoActivity.class));
        } else if (id == R.id.info_layout) {
            startActivity(new Intent(getContext(), UserInfoActivity.class));
        } else if (id == R.id.user_gz) {
            startActivity(new Intent(getContext(), UserNewsActivity.class).putExtra("type",2));
        } else if (id == R.id.user_zan) {
            startActivity(new Intent(getContext(), UserNewsActivity.class).putExtra("type",0));
        } else if (id == R.id.user_notice_number) {
        } else if (id == R.id.user_pl) {
            startActivity(new Intent(getContext(), UserNewsActivity.class).putExtra("type",1));
        } else if (id == R.id.user_fk) {
            startActivity(new Intent(getContext(), UserFeedbackActivity.class));
        } else if (id == R.id.user_update) {
            version();
        } else if (id == R.id.exit_btn) {
            MsgUtils.showMDMessage(getActivity(), "?????????????????????", "??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
//                    shared.edit().putBoolean(Constants.UserIsLogin, false).putString(Constants.UserToken, "").apply();
//                    WebHelper.init().refush();
                    logout();
                }
            }, "??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void version(){
        actHelper.showProgressDialog("????????????,?????????...");
        Https.with(getActivity()).url(BaseApi.Version).addParam("type",3).get()
                .enqueue(new Callback<Version>() {
                    @Override
                    public void success(final Version data) {
                        actHelper.dismissProgressDialog();
                        if(data!=null&&AllenManager.getInstance().isNewVersion(data.getAppVersion())){
                            if(data.isAppCompel()){
                                download(Constants.url+data.getAppPath());
                            }else{
                                MsgUtils.showNotOutMDMessage(getActivity(), "?????????", data.getAppDesc(), "??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        download(Constants.url+data.getAppPath());
                                    }
                                });
                            }
                        }else{
                            MsgUtils.showMDMessage(getActivity(),"??????????????????!");
                        }
                    }

                    @Override
                    public void fail(Response response) {
                        actHelper.dismissProgressDialog();
                        MsgUtils.showMDMessage(getActivity(),response.getMsg());
                    }
                });
    }
    private void download(String url){
        final ProgressDialog udialog = new ProgressDialog(getActivity(), allen.frame.R.style.Base_V21_Theme_AppCompat_Light_Dialog);
        udialog.setTitle("????????????");
        udialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        udialog.setMax(100);
        udialog.setProgress(0);
        udialog.setCancelable(false);
        udialog.show();
        Https.with(getActivity()).url(url)
                .download(new Callback<File>() {
                    @Override
                    public void success(File data) {
                        udialog.dismiss();
                        FileIntent.installApk(getActivity(),data);
                    }

                    @Override
                    public void onProgress(long total, long current) {
                        udialog.setProgress((int) (100*current*total));
                        udialog.setProgressNumberFormat(String.format("%s/%s", StringUtils.formatFileSize(current),StringUtils.formatFileSize(total)));
                    }

                    @Override
                    public void fail(Response response) {
                        udialog.dismiss();
                        MsgUtils.showMDMessage(getActivity(),response.getMsg());
                    }
                });
    }

}
