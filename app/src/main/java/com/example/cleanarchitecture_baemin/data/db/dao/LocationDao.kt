package com.example.cleanarchitecture_baemin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchitecture_baemin.data.entitiy.LocationLatLngEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM LocationLatLngEntity WHERE id=:id")
    suspend fun get(id: Long): LocationLatLngEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationLatLngEntity: LocationLatLngEntity)

    @Query("DELETE FROM LocationLatLngEntity WHERE id=:id")
    suspend fun delete(id: Long)

}