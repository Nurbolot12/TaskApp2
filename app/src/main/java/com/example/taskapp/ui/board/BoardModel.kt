package com.example.taskapp.ui.board

data class BoardModel(
    var img: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var isLast: Boolean? = false,
    var bg: Int,
) : java.io.Serializable

