package com.example.ritwick.webby;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
{

    EditText text;
    WebView web;
    Button gobutton;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(EditText) findViewById(R.id.editText);
        web=(WebView) findViewById(R.id.webView);
        gobutton=(Button) findViewById(R.id.button);
        progress=(ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);


        web.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView w,String url, Bitmap favicon)
            {
                text.setText(w.getUrl().toString());
                gobutton.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                super.onPageStarted(w, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                progress.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

        web.getSettings().setJavaScriptEnabled(true);

        web.setWebChromeClient(new WebChromeClient()
        {
            public void onProgressChanged(WebView view, int progressdata)
            {
                progress.setProgress(progressdata,true);
            }
        });

        web.setDownloadListener(new DownloadListener()
        {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
            {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        text.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.equals(""));
                {
                    gobutton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }

        });

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1);
    }

    @Override
    public void onBackPressed()
    {
        if(web.isFocused() && web.canGoBack())
            web.goBack();
        else
            super.onBackPressed();
    }

    public void gobuttonclicked(View v)
    {
        gobutton.setVisibility(View.GONE);
        String url="https://www."+text.getText().toString()+".com";
        web.loadUrl(url);
        web.requestFocus();
    }


}
