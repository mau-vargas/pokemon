package com.android.pokemon.domain.entity.Detail

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)