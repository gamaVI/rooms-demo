package com.example.businessscout.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BussinessCollectionCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossRef(crossRef: BussinessCollectionCrossRef)

}