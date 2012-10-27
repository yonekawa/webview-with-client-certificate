package jp.mog2dev.webkit;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import jp.mog2dev.webkit.cert.ClientCertificate;

/**
 * WebView enhanced to use client certificate.
 * @author yonekawa
 */
public class ClientCertificateWebView extends WebView
{
    protected ClientCertificate certificate = null;

    public ClientCertificateWebView( Context context )
    {
        super( context );
    }

    public ClientCertificateWebView( Context context, AttributeSet attributes )
    {
        super( context, attributes );
    }

    /**
     * Specify to use client certificate for authentication.
     * @param certificate
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public void useCertificate( ClientCertificate certificate ) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException, IllegalArgumentException, ClassNotFoundException, IllegalAccessException
    {
        this.certificate = certificate;

        KeyStore keyStore = this.createKeyStore( certificate );
        SSLContext sslContext = this.createSSLContext( keyStore, certificate );
        this.setSslContext( sslContext );
    }

    public ClientCertificate getClientCertificate()
    {
        return this.certificate;
    }

    public void setClientCertificate(ClientCertificate certificate)
    {
        this.certificate = certificate;
    }

    protected KeyStore createKeyStore( ClientCertificate certificate ) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
    {
        InputStream certificateFileStream = new FileInputStream( new File( certificate.getPath() ) );

        KeyStore keyStore = KeyStore.getInstance( certificate.getAlgorithm() );
        String password = certificate.getPassword();
        keyStore.load( certificateFileStream, password != null ? password.toCharArray() : null );

        certificateFileStream.close();

        return keyStore;
    }

    protected SSLContext createSSLContext( KeyStore keyStore, ClientCertificate certificate ) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException
    {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance( "X509" );

        String password = certificate.getPassword();
        keyManagerFactory.init( keyStore, password != null ? password.toCharArray() : null );
        SSLContext sslContext = SSLContext.getInstance( "TLS" );
        sslContext.init( keyManagerFactory.getKeyManagers(), certificate.getTrustManagers(), null );
        return sslContext;
    }

    protected void setSslContext( SSLContext sslContext ) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        Class<?> httpsConnection = Class.forName( "android.net.http.HttpsConnection" );
        Field[] fieldlist = httpsConnection.getDeclaredFields();
        for( int i = 0; i < fieldlist.length; i++ )
        {
            Field field = fieldlist[i];
            if( field.getName().equals( "mSslSocketFactory" ) )
            {
                field.setAccessible( true );
                field.set( null, sslContext.getSocketFactory() );
            }
        }
    }
}
