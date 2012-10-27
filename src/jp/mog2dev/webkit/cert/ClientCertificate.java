package jp.mog2dev.webkit.cert;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class ClientCertificate
{
    protected String algorithm;
    protected String path;
    protected String password;
    protected TrustManager[] trustManagers;

    public ClientCertificate()
    {
        this.algorithm = "PKCS12";
        this.trustManagers = new TrustManager[]{ new X509TrustManager()
        {
            public X509Certificate[] getAcceptedIssuers()
            {
                return null;
            }
            public void checkClientTrusted( X509Certificate[] certs, String authType )
            {
            }
            public void checkServerTrusted( X509Certificate[] certs, String authType )
            {
            }
        } };
        this.path = "";
        this.password = "";
    }

    public String getAlgorithm()
    {
        return this.algorithm;
    }

    public void setAlgorithm( String algorithm )
    {
        this.algorithm = algorithm;
    }

    public String getPath()
    {
        return this.path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public TrustManager[] getTrustManagers()
    {
        return this.trustManagers;
    }

    public void setTrustManagers( TrustManager[] trustManagers )
    {
        this.trustManagers = trustManagers;
    }
}
