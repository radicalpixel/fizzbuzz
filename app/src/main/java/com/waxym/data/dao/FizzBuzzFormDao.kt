package com.waxym.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waxym.data.model.FizzBuzzForm
import com.waxym.data.model.FizzBuzzForm.Companion.COL_BUZZ_LABEL
import com.waxym.data.model.FizzBuzzForm.Companion.COL_BUZZ_MULTIPLE
import com.waxym.data.model.FizzBuzzForm.Companion.COL_HIT_COUNT
import com.waxym.data.model.FizzBuzzForm.Companion.COL_FIZZ_LABEL
import com.waxym.data.model.FizzBuzzForm.Companion.COL_FIZZ_MULTIPLE
import com.waxym.data.model.FizzBuzzForm.Companion.COL_LIMIT
import com.waxym.data.model.FizzBuzzForm.Companion.TABLE_NAME

@Dao
interface FizzBuzzFormDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COL_HIT_COUNT DESC LIMIT :limit")
    suspend fun getMostFrequent(limit: Int): List<FizzBuzzForm>

    @Query("SELECT SUM($COL_HIT_COUNT) FROM $TABLE_NAME")
    suspend fun sumHitCount(): Int?

    @Query("SELECT * FROM $TABLE_NAME WHERE $COL_FIZZ_MULTIPLE LIKE :fizzMultiple AND $COL_FIZZ_LABEL LIKE :fizzLabel AND $COL_BUZZ_MULTIPLE LIKE :buzzMultiple AND $COL_BUZZ_LABEL LIKE :buzzLabel AND $COL_LIMIT LIKE :limit")
    suspend fun get(fizzMultiple: Int, fizzLabel: String, buzzMultiple: Int, buzzLabel: String, limit: Int): FizzBuzzForm?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: FizzBuzzForm)
}