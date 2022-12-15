package com.xxun.watch.picture_translate.view_model

import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.*
import com.xxun.watch.picture_translate.repository.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoPreviewViewModel : ViewModel() {
    private val netRepository = NetRepository()
    fun identityPicture(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: 需要判断文件大小
            val base64 = picture2Base64(path)
            withContext(Dispatchers.Main) {
                val bean = netRepository.identityPicture(base64)
                LogUtils.d(bean)
            }
        }
    }

    @WorkerThread
    private fun picture2Base64(path: String): String {
        val imageByteArr = FileIOUtils.readFile2BytesByChannel(path)
        return EncodeUtils.base64Encode2String(imageByteArr)
    }

}