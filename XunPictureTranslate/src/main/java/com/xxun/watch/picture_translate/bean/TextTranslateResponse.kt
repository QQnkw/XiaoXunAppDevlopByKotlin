package com.xxun.watch.picture_translate.bean

data class TextTranslateResponse(
    val code: Int,
    val datajs: Datajs,
    val msg: String
)

data class Datajs(
    val from: String,
    val to: String,
    val trans_result: TransResult
)

data class TransResult(
    val dst: String,
    val src: String
)