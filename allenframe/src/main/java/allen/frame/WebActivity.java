package allen.frame;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import allen.frame.tools.Constants;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class WebActivity extends AllenBaseActivity {
    Toolbar bar;
    WebView web;
    private String title;
    private String url;

    @Override
    protected boolean isStatusBarColorWhite() {
        return false;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web;
    }

    @Override
    protected void initBar() {
        title = getIntent().getStringExtra(Constants.Key_1);
        bar = findViewById(R.id.toolbar);
        setToolbarTitle(bar, title,true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        url = getIntent().getStringExtra(Constants.Key_2);
        web = findViewById(R.id.web);
        WebSettings settings = web.getSettings();
        //支持js
        settings.setJavaScriptEnabled(true);
        // 解决图片不显示
        settings.setBlockNetworkImage(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        //设置可以支持缩放
        settings.setSupportZoom(false);
        //扩大比例的缩放
        settings.setUseWideViewPort(false);
        //设置是否出现缩放工具
        settings.setBuiltInZoomControls(false);

        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        web.loadUrl(url);
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(web.canGoBack()){
                    web.goBack();
                }else{
                    finish();
                }
            }
        });
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            // 重写此方法能够让webview处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });
    }
}
