package jp.mog2dev.webkit;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import android.webkit.ClientCertRequestHandler;
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

    /**
     * If you want to use client certificate by Android 4.x WebView, you should uncomment this.
     * However this method uses private class `ClientCertRequestHandler`.
     * You need to create original android.jar(contains all classes)
     * Please read this issue.
     * https://github.com/yonekawa/webview-with-client-certificate/issues/1
     */
    /*
    @Override
    public void onReceivedClientCertRequest( WebView view, ClientCertRequestHandler handler, String host_and_port )
    {
        try {
            ClientCertificateWebView webview = (ClientCertificateWebView)view;
            KeyStore store = webview.createKeyStore( webview.getClientCertificate() );

            PrivateKey privateKey = null;
            X509Certificate[] certificates = null;
            Enumeration<String> e = store.aliases();
            for (; e.hasMoreElements(); ) {
                String alias = e.nextElement();
                if (store.isKeyEntry(alias)) {
                    KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry)store.getEntry( alias, null );
                    privateKey = entry.getPrivateKey();
                    certificates = (X509Certificate[])entry.getCertificateChain();
                }
            }
            handler.proceed( privateKey, certificates );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
