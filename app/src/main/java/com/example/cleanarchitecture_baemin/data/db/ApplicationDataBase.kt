package com.example.cleanarchitecture_baemin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cleanarchitecture_baemin.data.db.dao.LocationDao
import com.example.cleanarchitecture_baemin.data.db.dao.RestaurantDao
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity
import com.example.cleanarchitecture_baemin.data.entitiy.RestaurantEntity

@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDataBase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao(): RestaurantDao
}