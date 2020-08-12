package com.kasai.stadium.tv.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.kasai.stadium.tv.R;

public class HtmlView extends LinearLayout {
    private android.webkit.WebView webView;

    public HtmlView(Context context) {
        this(context, null);
    }

    public HtmlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HtmlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_webview, this, true);
        webView = findViewById(R.id.webView);
        webView.setBackgroundColor(0);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.supportMultipleWindows();//支持多窗口

        webSettings.setUseWideViewPort(false);//调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局

        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setDisplayZoomControls(false);//支持直接隐藏缩放控件

        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);//允许访问文件

        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片

        webSettings.setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
        webSettings.setBlockNetworkImage(false);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//缓存

        webSettings.setTextZoom(100); // 通过百分比来设置文字的大小，默认值是100。

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setLayerType(View.LAYER_TYPE_NONE, null);


//        webView.addJavascriptInterface(new AndroidJS(), "android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset();
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }


        });
        webView.setWebChromeClient(new WebChromeClient());
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    public void loadHtml(String html) {
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

//    public String getHtmlData(String bodyHTML) {
//        String head =
//                "<head>" + "<style>body{padding: 0; margin: 0;} p{padding: 0; margin: 0; color: #666666; font-size: 12px;}</style>" + "</head>";
//        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
//    }

    public String getHtmlData(String bodyHTML) {
        String head =
                "<head>" + "<style>body{padding: 0; margin: 0;} p{padding: 0; margin: 0;}</style>" + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
