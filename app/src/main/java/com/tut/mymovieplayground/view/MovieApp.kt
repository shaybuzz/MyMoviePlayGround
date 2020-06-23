package com.tut.mymovieplayground.view

import android.app.Application
import timber.log.Timber

class MovieApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}