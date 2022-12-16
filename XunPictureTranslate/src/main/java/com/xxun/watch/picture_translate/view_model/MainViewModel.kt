package com.xxun.watch.picture_translate.view_model

import android.graphics.BitmapFactory
import android.os.SystemClock
import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.*
import com.xxun.watch.picture_translate.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

class MainViewModel : ViewModel() {

    var path = ""

    @WorkerThread
    fun saveBitmapByteArrToFile(data: ByteArray?): Boolean {
        return data?.let {
            val bitmap =
                BitmapFactory.decodeByteArray(it, 0, it.size, BitmapFactory.Options().apply {
                    inSampleSize = 4
                })
            val imageByteArray = ImageUtils.bitmap2Bytes(bitmap)
            bitmap.recycle()
            path = createImagePath()
            FileIOUtils.writeFileFromBytesByChannel(path, imageByteArray, true)
        } ?: false
    }

    @AnyThread
    fun createImagePath(): String {
        val appCachePath = PathUtils.getExternalAppCachePath()
        val currentThreadTimeMillis = SystemClock.currentThreadTimeMillis()
        return appCachePath + File.separator + currentThreadTimeMillis + ".jpg"
    }
}