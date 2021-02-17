package com.waxym.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waxym.data.dao.FizzBuzzFormDao
import com.waxym.data.model.FizzBuzzForm

@Database(entities = [FizzBuzzForm::class], version = 1)
abstract class FizzBuzzDatabase : RoomDatabase() {
    abstract fun formDao(): FizzBuzzFormDao

    companion object {
        const val NAME = "FIZZ-BUZZ-DATABASE"
    }
}