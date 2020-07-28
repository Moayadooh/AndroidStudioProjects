package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    WebView wv1;
    EditText etURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv1 = findViewById(R.id.wv1);
        etURL = findViewById(R.id.etURL);
        wv1.loadUrl("https://www.google.com/");

        //wv1.setWebViewClient(new myWebView());
        wv1.getSettings().setJavaScriptEnabled(true);
        //wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }

    public void buGo(View view) {
        LoadURL(etURL.getText().toString());
    }

    public void buBack(View view) {
        wv1.goBack();
    }

    public void buForward(View view) {
        wv1.goForward();
    }

    //String URL;
    void LoadURL(String URL){
        //this.URL = URL;
        wv1.loadUrl(URL);
    }

    /*private class myWebView extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(URL);
            return true;
        }
    }*/
}
