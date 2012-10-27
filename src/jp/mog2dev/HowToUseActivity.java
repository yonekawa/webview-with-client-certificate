package jp.mog2dev;

import jp.mog2dev.webkit.ClientCertificateWebView;
import jp.mog2dev.webkit.ClientCertificateWebViewClient;
import jp.mog2dev.webkit.cert.ClientCertificate;

import jp.mog2dev.webkit.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class HowToUseActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        ClientCertificateWebView webview = (ClientCertificateWebView)this.findViewById( R.id.webview );
        webview.setWebViewClient( new ClientCertificateWebViewClient() );

        try
        {
            ClientCertificate certificate = new ClientCertificate();
            certificate.setPath( Environment.getExternalStorageDirectory() + "/cert.pfx" );
            certificate.setPassword( "password" );
            webview.useCertificate( certificate );

            webview.loadUrl( "url" );
        }
        catch( Throwable tr )
        {
            Log.e( "WebViewWithClientCertificate", tr.getMessage(), tr );

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( this );
            alertDialogBuilder.setIcon( android.R.drawable.ic_dialog_alert );
            alertDialogBuilder.setTitle( "error" );
            alertDialogBuilder.setMessage( tr.getMessage() );
            alertDialogBuilder.show();
        }
    }
}