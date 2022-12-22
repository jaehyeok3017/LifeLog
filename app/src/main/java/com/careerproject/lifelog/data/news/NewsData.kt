package com.careerproject.lifelog.data.news

import com.google.gson.annotations.SerializedName

data class NewsData(
    val currentCount: Int,

    @SerializedName("`data`")
    val data: List<Data>,

    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)