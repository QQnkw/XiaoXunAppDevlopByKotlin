package com.xxun.watch.picture_translate.repository

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import com.blankj.utilcode.util.LogUtils
import com.xxun.watch.picture_translate.bean.IdentityPictureResponse
import com.xxun.watch.picture_translate.bean.TextTranslateResponse
import com.xxun.watch.picture_translate.camera.JsonCallback
import com.xxun.watch.picture_translate.camera.OkHttp3NetUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetRepository {
    @MainThread
    suspend fun identityPicture(@NonNull base64: String): IdentityPictureResponse {
        return suspendCancellableCoroutine { continuation ->
            OkHttp3NetUtils.getInstance()
                .imageIdentifyToText(base64, "123456", object : JsonCallback<IdentityPictureResponse>() {
                    override fun onSuccess(p0: IdentityPictureResponse) {
                        continuation.resume(p0)
                    }

                    override fun onError(exception: Exception) {
                        super.onError(exception)
                        continuation.resumeWithException(exception)
                    }
                })
        }
    }
    @MainThread
    suspend fun textTranslate(
        @NonNull sourceLanguage: String,
        @NonNull targetLanguage: String,
        @NonNull text: String
    ): Any? {
        return suspendCancellableCoroutine { continuation ->
            OkHttp3NetUtils.getInstance()
                .textTranslate("", "", "", "123456", object : JsonCallback<TextTranslateResponse>() {
                    override fun onSuccess(p0: TextTranslateResponse) {
                        continuation.resume(p0)
                    }

                    override fun onError(exception: Exception) {
                        super.onError(exception)
                        continuation.resumeWithException(exception)
                    }
                })
        }
    }

}