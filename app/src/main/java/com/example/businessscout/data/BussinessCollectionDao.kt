package com.example.businessscout.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BussinessCollectionDao {
    @Insert
     fun insertCollection(collection: BussinessCollection)

    @Query("SELECT * FROM BussinessCollection")
     fun getAllCollections(): List<BussinessCollection>


    @Query("DELETE FROM BussinessCollection WHERE id = :id")
     fun deleteCollection(id: Int)


    // update
    @Query("UPDATE BussinessCollection SET collectionName = :collectionName, colorHex = :colorHex WHERE id = :id")
     fun updateCollection(id: Int, collectionName: String, colorHex: String)

}