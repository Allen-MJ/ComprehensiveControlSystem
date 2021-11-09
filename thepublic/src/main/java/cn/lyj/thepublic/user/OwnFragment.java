package cn.lyj.thepublic.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import allen.frame.ActivityHelper;
import allen.frame.AllenManager;
import allen.frame.entry.Response;
import allen.frame.net.Callback;
import allen.frame.net.Https;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.CircleImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lyj.thepublic.LoginActivity;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;
import cn.lyj.thepublic.data.API;

public class OwnFragment extends Fragment {

    Unbinder unbinder;
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

    private ActivityHelper helper;
    private SharedPreferences shared;
    private int relateState = -1;

    public static OwnFragment init() {
        OwnFragment fragment = new OwnFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_own, container, false);
        helper = new ActivityHelper(getActivity(), view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shared = helper.getSharedPreferences();
        initUI(view);
        addEvent(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initUI(View view) {
        userName.setText(StringUtils.empty(shared.getString(Constants.UserName, "")) ? "未登录" : shared.getString(Constants.UserName, ""));
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
                        MsgUtils.showLongToast(getContext(), "退出成功!");
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
            startActivity(new Intent(getContext(),UserInfoActivity.class));
        } else if (id == R.id.user_dw) {
        } else if (id == R.id.user_photo) {
        } else if (id == R.id.info_layout) {
        } else if (id == R.id.user_gz) {
            startActivity(new Intent(getContext(),UserNewsActivity.class).putExtra("type",2));
        } else if (id == R.id.user_zan) {
            startActivity(new Intent(getContext(),UserNewsActivity.class).putExtra("type",0));
        } else if (id == R.id.user_notice_number) {
        } else if (id == R.id.user_pl) {
            startActivity(new Intent(getContext(),UserNewsActivity.class).putExtra("type",1));
        } else if (id == R.id.user_fk) {
        } else if (id == R.id.user_update) {
        } else if (id == R.id.exit_btn) {
            MsgUtils.showMDMessage(getActivity(), "确认退出登录？", "退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
//                    shared.edit().putBoolean(Constants.UserIsLogin, false).putString(Constants.UserToken, "").apply();
//                    WebHelper.init().refush();
                    logout();
                }
            }, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
        }
    }
}
