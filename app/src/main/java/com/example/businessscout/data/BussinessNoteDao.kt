package com.example.businessscout.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BussinessNoteDao {
    @Insert
    suspend fun insertBusinessNote(note: BussinessNote)

    @Query("SELECT * FROM business_notes WHERE businessId = :businessId")
    suspend fun getNotesForBusiness(businessId: Int): List<BussinessNote>

}
