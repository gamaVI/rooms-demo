package com.example.businessscout.data

import androidx.room.Entity

@Entity(primaryKeys = ["businessId", "collectionId"])
data class BussinessCollectionCrossRef(
    val businessId: Int,
    val collectionId: Int
)
