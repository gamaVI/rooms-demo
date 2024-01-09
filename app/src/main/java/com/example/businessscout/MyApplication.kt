package com.example.businessscout

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.example.businessscout.data.AppDatabase
import com.example.businessscout.data.Bussiness
import com.example.businessscout.data.BussinessCollection
import com.example.businessscout.data.BussinessCollectionCrossRef


class MyApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "collection_updates",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel description"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        populateDatabaseWithDummyData()
    }

    private fun populateDatabaseWithDummyData() {
        val thread = Thread {
            // Insert a dummy collection
            val dummyCollection = BussinessCollection(collectionName = "Test", colorHex = "#eeeeee")
            database.businessCollectionDao().insertCollection(dummyCollection)

            // Create 10 dummy businesses
            for (i in 1..10) {
                val dummyBusiness = Bussiness(
                    naziv = "Business $i",
                    gsm = "040 777 ${100 + i}",
                    email = "contact${i}@business.com",
                    website = "www.business${i}.com",
                    address = "Address $i",
                    city = "Ljubljana",
                    postalCode = "1000",
                    tax = "SI12345$i",
                    lat = 46.056946 + (i * 0.0001),
                    lng = 15.505751 + (i * 0.0001),
                    datumVpisa = "2023-09-${20 + i}"
                )
              //  database.bussinessDao().insertBusiness(dummyBusiness)

            }
        }
        thread.start()
    }
}
