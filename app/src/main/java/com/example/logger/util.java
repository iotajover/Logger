package com.example.logger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public  class util {
    private Context context;

    public util(Context context) {
        this.context = context;
    }

    public boolean verificarConexionInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
