package com.example.cleanarchitecture_baemin.di

import android.content.Context
import androidx.room.Room
import com.example.cleanarchitecture_baemin.data.db.ApplicationDataBase

fun provideDB(context: Context): ApplicationDataBase =
    Room.databaseBuilder(context, ApplicationDataBase::class.java, ApplicationDataBase.DB_NAME).build()

fun provideLocationDao(database: ApplicationDataBase) = database.LocationDao()