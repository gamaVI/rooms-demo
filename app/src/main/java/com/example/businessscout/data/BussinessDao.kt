package com.example.businessscout.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BussinessDao {
    @Insert
     fun insertBusiness(business: Bussiness)

    @Query("SELECT * FROM businesses")
     fun getAllBusinesses(): List<Bussiness>

    @Query("SELECT * FROM businesses " +
            "JOIN bussinesscollectioncrossref ON businesses.id =bussinesscollectioncrossref.businessId " +
            "WHERE bussinesscollectioncrossref.collectionId = :collectionId")
    fun getBusinessesInCollection(collectionId: Int): List<Bussiness>


     // get bussinesses which are in collection with id = collectiond

    // Add other necessary queries
}
