package com.xxun.watch.picture_translate.audio

import android.media.AudioManager
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class VoicePlayTool : DefaultLifecycleObserver {
    private val instance = MpListUtils.getInstance()
    private val mediaPlayer = instance.mediaPlayerUtils
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        instance.pauseAllMp()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        instance.stopAllMp()
    }

    fun setData(url: String) {
        mediaPlayer.setData(url, AudioManager.STREAM_MUSIC)
    }

    fun pauseVoice() {
        instance.pauseAllMp()
    }
}