package com.example.businessscout.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_notes")
data class BussinessNote(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    val businessId: Int,
    val note: String
)

