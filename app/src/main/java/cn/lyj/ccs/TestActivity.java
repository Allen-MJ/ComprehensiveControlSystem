package cn.lyj.ccs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import allen.frame.AllenBaseActivity;
import allen.frame.AllenMapActivity;
import allen.frame.tools.Constants;
import allen.frame.tools.MsgUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class TestActivity extends AllenBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.into_1)
    AppCompatButton into1;
    @BindView(R.id.into_2)
    AppCompatButton into2;
    @BindView(R.id.into_3)
    AppCompatButton into3;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.test_layout;
    }

    @Override
    protected void initBar() {
        setToolbarTitle(toolbar,"测试界面",false);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }

    @Override
    public void onBackPressed() {
        actHelper.doClickTwiceExit(toolbar);
    }

    @OnClick({R.id.into_1, R.id.into_2, R.id.into_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.into_1:
                if (ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.CAMERA) != PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Constants.PERMISSION_REQUEST_ONE);
                } else {
                    ScanUtil.startScan(TestActivity.this, Constants.REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().create());
                }
                break;
            case R.id.into_2:
                break;
            case R.id.into_3:
                if (ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            Constants.PERMISSION_REQUEST_TWO);
                } else {
                    startActivity(new Intent(context, AllenMapActivity.class));
                }
                break;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants
                    .PERMISSION_REQUEST_ONE:
                if (checkIsOk(grantResults)) {
                    ScanUtil.startScan(TestActivity.this, Constants.REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().create());
                } else {
                    MsgUtils.showMDMessage(context, "请开通权限!");
                }
                break;
            case Constants.PERMISSION_REQUEST_TWO:
                startActivity(new Intent(context, AllenMapActivity.class));
                break;
        }
    }

    private boolean checkIsOk(int[] grantResults) {
        boolean isok = true;
        for (int i : grantResults) {
            isok = isok && (i == PackageManager.PERMISSION_GRANTED);
        }
        return isok;
    }
}
