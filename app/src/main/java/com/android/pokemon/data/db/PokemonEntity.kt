package com.android.pokemon.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "url") var url: String = "",
    @ColumnInfo(name = "favorite") var favorite: Boolean = false)

