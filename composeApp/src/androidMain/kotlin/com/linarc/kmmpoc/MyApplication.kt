package com.linarc.kmmpoc

import android.app.Application
import com.linarc.kmmpoc.data.local.appContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
