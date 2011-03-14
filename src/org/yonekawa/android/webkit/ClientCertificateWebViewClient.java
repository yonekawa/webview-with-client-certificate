package org.yonekawa.android.webkit;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ClientCertificateWebViewClient extends WebViewClient
{
    @Override
    public boolean shouldOverrideUrlLoading( WebView view, String url )
    {
        view.loadUrl( url );
        return true;
    }
}
