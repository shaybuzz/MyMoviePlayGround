package com.tut.mymovieplayground.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri


fun Context.navigateToUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}

fun Context.hasInternet(): Boolean {
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo()
        .isConnected();
}