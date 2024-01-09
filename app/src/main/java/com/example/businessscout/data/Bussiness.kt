package com.example.businessscout.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesses")
data class Bussiness(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val naziv: String?,
    val gsm: String?,
    val email: String?,
    val website: String?,
    val address: String?,
    val city: String?,
    val postalCode: String?,
    val tax: String?,
    val datumVpisa: String?,
    val lat : Double,
    val lng : Double,

    )


