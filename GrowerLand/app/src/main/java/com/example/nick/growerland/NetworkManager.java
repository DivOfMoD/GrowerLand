package com.example.nick.growerland;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.Uri;

public final class NetworkManager {

    private static NetworkInfo getNetworkInfo(final Context context) {
        final android.net.ConnectivityManager cm = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(final Context context) {
        final NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static void goToUrl(final Context pContext, final String url) {
        final Uri uriUrl = Uri.parse(url);
        final Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        pContext.startActivity(launchBrowser);
    }
}
