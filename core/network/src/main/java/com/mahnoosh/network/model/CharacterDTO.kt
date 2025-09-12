package com.mahnoosh.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO(
    @SerialName("name")
    val name: String? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("culture")
    val culture: String? = null,
    @SerialName("born")
    val born: String? = null,
    @SerialName("died")
    val died: String? = null,
    @SerialName("titles")
    val titles: List<String?>? = null,
    @SerialName("aliases")
    val aliases: List<String?>? = null,
    @SerialName("tvSeries")
    val tvSeries: List<String?>? = null,
    @SerialName("playedBy")
    val playedBy: List<String?>? = null,
    @SerialName("father")
    val father: String? = null,
    @SerialName("mother")
    val mother: String? = null
)
