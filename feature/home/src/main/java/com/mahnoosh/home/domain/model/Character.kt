package com.mahnoosh.home.domain.model

internal data class Character(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val titles: List<String>,
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
    val father: String,
    val mother: String
)