package com.xxun.watch.picture_translate

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        LogUtils.getConfig().globalTag = "PictureTranslate"
        Utils.init(this)
    }
}