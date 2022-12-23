package com.xxun.watch.picture_translate.view_model

import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.xxun.watch.picture_translate.R
import com.xxun.watch.picture_translate.bean.TransResult
import com.xxun.watch.picture_translate.repository.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateResultViewModel : ViewModel() {
    private val netRepository = NetRepository()
    var sourceLanguage = "en"
    var targetLanguage = "cn"
    private var en2cn = true;
    val translateResultLiveData = MutableLiveData<TransResult>()
    fun translateText(str: String) {
        viewModelScope.launch {
            try {
                val base64 = text2Base64(str)
                val bean = netRepository.textTranslate(sourceLanguage, targetLanguage, base64)
                translateResultLiveData.value = bean.datajs.trans_result
            } catch (e: Exception) {
                translateResultLiveData.value = TransResult("", "")
                LogUtils.e(e)
            }
        }
    }

    @WorkerThread
    private fun text2Base64(text: String): String {
        return EncodeUtils.base64Encode2String(text.toByteArray())
    }

    fun changeSourceTargetLanguage(): Boolean {
        if (en2cn) {
            sourceLanguage = "cn"
            targetLanguage = "en"
            en2cn = false
        } else {
            sourceLanguage = "en"
            targetLanguage = "cn"
            en2cn = true
        }
        return en2cn
    }
}