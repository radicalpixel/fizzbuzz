package com.waxym

import android.app.Application
import androidx.room.Room
import com.waxym.data.database.FizzBuzzDatabase
import com.waxym.utils.injection.Bob

class FizzBuzzApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Bob[FizzBuzzDatabase::class] = Room.databaseBuilder(this, FizzBuzzDatabase::class.java, FizzBuzzDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}