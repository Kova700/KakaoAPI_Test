package com.example.testkakaobookapi

// @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
// @SerializedName()로 변수명을 일치시켜주면 클래스 변수명이 달라도 알아서 매핑

data class BookResponse(
    val documents: List<Document>,
    val meta: Meta
)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)

data class Document(
    val authors: List<String>,
    val contents: String,
    val datetime: String,
    val isbn: String,
    val price: Int,
    val publisher: String,
    val sale_price: Int,
    val status: String,
    val thumbnail: String,
    val title: String,
    val translators: List<String>,
    val url: String
)

