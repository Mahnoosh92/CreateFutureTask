package com.mahnoosh.home.data.mapper

import com.mahnoosh.home.domain.model.Character
import com.mahnoosh.network.model.CharacterDTO

internal fun CharacterDTO.toCharacter() = Character(
    name = name ?: "",
    gender = gender ?: "",
    culture = culture ?: "",
    born = born ?: "",
    died = died ?: "",
    titles = titles?.map { it ?: "" } ?: emptyList(),
    aliases = aliases?.map { it ?: "" } ?: emptyList(),
    tvSeries = tvSeries?.map { it ?: "" } ?: emptyList(),
    playedBy = playedBy?.map { it ?: "" } ?: emptyList(),
    father = father ?: "",
    mother = mother ?: "",
)