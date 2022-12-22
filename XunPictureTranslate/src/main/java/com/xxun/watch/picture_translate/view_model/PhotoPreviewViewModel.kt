package com.xxun.watch.picture_translate.view_model

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.*
import com.xxun.watch.picture_translate.bean.IdentityPictureResponse
import com.xxun.watch.picture_translate.bean.Word
import com.xxun.watch.picture_translate.repository.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoPreviewViewModel : ViewModel() {
    private val netRepository = NetRepository()
    val textInfoLiveData = MutableLiveData<List<Word>>()
    fun identityPicture(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: 需要判断文件大小
            val base64 = picture2Base64(path)
            withContext(Dispatchers.Main) {
                try {
                    val bean = netRepository.identityPicture(base64)
                    val textList = mutableListOf<Word>()
                    bean.datajs.words.forEach {
                        textList.add(it)
                    }
                    textInfoLiveData.value = textList
                } catch (e: Exception) {
                    textInfoLiveData.value = listOf()
                    LogUtils.e(e)
                }
            }
        }
    }

    @WorkerThread
    private fun picture2Base64(path: String): String {
        val imageByteArr = FileIOUtils.readFile2BytesByChannel(path)
        return EncodeUtils.base64Encode2String(imageByteArr)
    }
}