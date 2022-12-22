package com.careerproject.lifelog.data.news

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("개체명(기업기관)")
    val name: String,

    @SerializedName("개체명(인물)")
    val people: String,

    @SerializedName("개체명(지역)")
    val place: String,

    @SerializedName("기고자")
    val writer: String,

    @SerializedName("본문")
    val text: String,

    @SerializedName("사건_사고 분류1")
    val accident1 : Any,

    @SerializedName("사건_사고 분류2")
    val accident2 : Any,

    @SerializedName("사건_사고 분류3")
    val accident3 : Any,

    @SerializedName("언론사")
    val company: String,

    @SerializedName("원본주소")
    val linkOrigin: String,

    @SerializedName("일자")
    val date: String,

    @SerializedName("제목")
    val title: String,

    @SerializedName("주소")
    val link: String,

    @SerializedName("키워드")
    val keyword: String,

    @SerializedName("통합 분류1")
    val classification : String,

    @SerializedName("통합 분류2")
    val classification2 : String,

    @SerializedName("통합 분류3")
    val classification3 : String,

    @SerializedName("특성추출")
    val extract : String
)