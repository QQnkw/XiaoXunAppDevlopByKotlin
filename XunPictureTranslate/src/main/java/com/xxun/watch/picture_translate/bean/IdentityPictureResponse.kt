package com.xxun.watch.picture_translate.bean

data class IdentityPictureResponse(
    val code: Int,
    val dataarray: List<Any>,
    val datajs: DataOne,
    val msg: String
)

data class DataOne(
    val words: List<Word>
)

data class Word(
    val conf: String,
    val content: String,
    val coord: List<Coord>
)

data class Coord(
    val x: Int,
    val y: Int
)