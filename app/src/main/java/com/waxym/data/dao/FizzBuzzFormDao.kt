package com.waxym.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waxym.data.model.FizzBuzzForm

@Dao
interface FizzBuzzFormDao {

    @Query("SELECT * FROM ${FizzBuzzForm.TABLE_NAME}")
    suspend fun getAll(): List<FizzBuzzForm>

    @Query("SELECT * FROM ${FizzBuzzForm.TABLE_NAME} WHERE ${FizzBuzzForm.ROW_ID} LIKE :id")
    suspend fun get(id: Long): FizzBuzzForm

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: FizzBuzzForm): Long
}