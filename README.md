# WebView with Client Certificate #

## DESCRIPTION ##
WebView of Android enhanced to use client certificate.

Android Browser cannot use client certificate authentication.
Because the browser doesn't use a credential storage in Android OS.
This is knows issues on Android OS.
If you use client certificate for authentication, Android OS cannot be supported.
http://code.google.com/p/android/issues/detail?id=8196

WebView with Client Certificate solves it.
ClientCertificateWebView class can use a client certificate file for authentication.

## USAGE ##
Please see HowToUseActivity.java.

    ClientCertificateWebView webview = new ClientCertificateWebView(this);

    ClientCertificate certificate = new ClientCertificate();
    certificate.setPath( "/path/to/certificate" );
    certificate.setPassword( "password" );
    webview.useCertificate( certificate );

    webview.loadUrl( "url" );

## Android 4.x ##
Android 4.x is not supported. Because it changed certificate handling method.
If you want to use this by Android 4.x, you should use private API with original android.jar(contains all classes).

Please read this issue.
https://github.com/yonekawa/webview-with-client-certificate/issues/1

## AUTHOR ##
Kenichi Yonekawa <tcgrim@gmail.com>

## LICENSE ##
This library is free software; you can redistribute it and/or modify it.