package cn.lyj.thepublic.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Constants;
import allen.frame.widget.CircleImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lyj.thepublic.R;
import cn.lyj.thepublic.R2;

public class UserInfoActivity extends AllenBaseActivity {
    @BindView(R2.id.title)
    AppCompatTextView title;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R2.id.user_name)
    AppCompatTextView userName;
    @BindView(R2.id.user_phone)
    AppCompatTextView userPhone;
    @BindView(R2.id.user_sex)
    AppCompatTextView userSex;
    @BindView(R2.id.user_level)
    AppCompatTextView userLevel;
    @BindView(R2.id.user_email)
    AppCompatTextView userEmail;
    @BindView(R2.id.user_address)
    AppCompatTextView userAddress;

    private SharedPreferences shared;

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
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @OnClick({R2.id.user_photo, R2.id.user_name, R2.id.user_phone, R2.id.user_sex, R2.id.user_level, R2.id.user_email, R2.id.user_address})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.user_photo) {
        } else if (id == R.id.user_name) {
        } else if (id == R.id.user_phone) {
        } else if (id == R.id.user_sex) {
        } else if (id == R.id.user_level) {
        } else if (id == R.id.user_email) {
        } else if (id == R.id.user_address) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
