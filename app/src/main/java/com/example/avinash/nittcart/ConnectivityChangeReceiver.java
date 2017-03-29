package com.example.avinash.nittcart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AVINASH on 3/29/2017.
 */
public class ConnectivityChangeReceiver
        extends BroadcastReceiver {

    static Snackbar snackbar = null;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(snackbar==null)createSnackbar();
        this.context = context;
        debugIntent(intent, "ConnectionStatus");
    }

    private void debugIntent(Intent intent, String tag) {
        Log.v(tag, "action: " + intent.getAction());
        Log.v(tag, "component: " + intent.getComponent());
        Bundle extras = intent.getExtras();

        if(!checkNetwork()){
            Log.d("status","disconnected");
            MainActivity.isInternetConnected = false;
            if(!snackbar.isShown())
                snackbar.show();
        }

        else {
            Log.d("status","connected");
            MainActivity.isInternetConnected = true;
            snackbar.dismiss();
        }

        if (extras != null) {
            for (String key: extras.keySet()) {
                Log.v(tag, "key [" + key + "]: " +
                        extras.get(key));
            }
        }
        else {
            Log.v(tag, "no extras");
        }
    }

    public void createSnackbar(){

         snackbar = Snackbar
                .make(MainActivity.coordinatorLayout, "No Internet Connection !!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkNetwork()) {
                            MainActivity.isInternetConnected = true;
                            //createSnackbar();
                            //snackbar.dismiss();
                        }
                        else{MainActivity.isInternetConnected = false;
                            createSnackbar();
                            snackbar.show();
                        }
                    }
                });

    }

    public boolean  checkNetwork(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (MainActivity.isInternetConnected = activeNetwork != null);

    }
}
