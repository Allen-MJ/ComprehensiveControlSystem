package allen.frame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import allen.frame.entry.Video;
import allen.frame.tools.Constants;
import allen.frame.widget.MediaController;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class AllenPlayerActivity extends AllenIMBaseActivity implements PLOnPreparedListener, PLOnBufferingUpdateListener, PLOnInfoListener, MediaController.OnShownListener, MediaController.OnHiddenListener, PLOnErrorListener {

    private PLVideoTextureView player;
    public static final int REQUEST_CAMERA_PERMISSION = 1003;
    private String path = "http://218.206.30.20/zqzzbwazd/vod/2020/08/18/f26ba67a28954e2c8764745b270f7d7a/h264_1200k_mp4.mp4";
    private Toolbar bar;
    private AppCompatTextView statInfo;
    private int isStream = 0;
    private Video video;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.player_activity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                if(checkIsOk(grantResults)){
                    player.setVideoPath(path);
                }else{
                    finish();
                }
                break;
            }
        }
    }

    private boolean checkIsOk(int[] grantResults){
        boolean isok = true;
        for(int i:grantResults){
            isok = isok && (i == PackageManager.PERMISSION_GRANTED);
        }
        return isok;
    }

    @Override
    protected void initBar() {
        video = (Video) getIntent().getSerializableExtra(Constants.Key_1);
        bar = findViewById(R.id.toolbar);
        setToolbarTitle(bar,video.getTitle());
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        player = findViewById(R.id.player);
        path = video.getUrl();
        isStream = video.getType();
        View loadingView = findViewById(R.id.player_loading);
        player.setBufferingIndicator(loadingView);
        statInfo = findViewById(R.id.player_statInfo);
        player.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        if(Video.VideoTypeLive==isStream){
            AVOptions options = new AVOptions();
            // 若设置为 1，则底层会进行一些针对直播流的优化
            options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
            // 快开模式，启用后会加快该播放器实例再次打开相同协议的视频流的速度
            options.setInteger(AVOptions.KEY_FAST_OPEN, 1);
            // 打开重试次数，设置后若打开流地址失败，则会进行重试
            options.setInteger(AVOptions.KEY_OPEN_RETRY_TIMES, 5);
            player.setAVOptions(options);
        }
        MediaController controller = new MediaController(context);
        player.setMediaController(controller);
        player.setOnPreparedListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnInfoListener(this);
        player.setOnErrorListener(this);
        controller.setOnShownListener(this);
        controller.setOnHiddenListener(this);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
            return;
        }
        player.setVideoPath(path);
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(player!=null){
            player.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player!=null){
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.stopPlayback();
        }
    }

    @Override
    public void onPrepared(int i) {
        player.start();
    }

    @Override
    public void onBufferingUpdate(int i) {

    }

    @Override
    public void onInfo(int what, int extra) {
        if(what==MEDIA_INFO_BUFFERING_START){

        }else if(what==MEDIA_INFO_BUFFERING_END){

        }else if(what==MEDIA_INFO_VIDEO_BITRATE||what==MEDIA_INFO_VIDEO_FPS){
            updateStatInfo();
        }
    }

    @Override
    public void onShown() {
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHidden() {
        bar.setVisibility(View.INVISIBLE);
    }

    private void updateStatInfo() {
        long bitrate = player.getVideoBitrate() / 1024/1024;
        final String stat = bitrate + "MB/秒";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statInfo.setText(stat);
            }
        });
    }

    @Override
    public boolean onError(int i) {
        if(i==ERROR_CODE_SEEK_FAILED){
            player.seekTo(0);
        }
        return true;
    }
}
