package com.xxun.watch.picture_translate.bean

data class IdentityPictureResponse(
    val code: Int,
    val dataarray: DataArray,
    val msg: String
)

data class DataArray(
    val words: List<Word>
)

data class Word(
    val conf: Float,
    val content: String,
    val coord: List<Coord>
)

data class Coord(
    val x: Int,
    val y: Int
)
