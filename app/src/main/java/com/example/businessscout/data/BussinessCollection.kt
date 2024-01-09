package com.example.businessscout.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BussinessCollection(
    val collectionName: String,
    val colorHex : String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
