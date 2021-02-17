package com.waxym.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = FizzBuzzForm.TABLE_NAME)
data class FizzBuzzForm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ROW_ID)
    val uid: Long = 0L,
    @ColumnInfo(name = ROW_FIZZ_MULTIPLE)
    val fizzMultiple: Int,
    @ColumnInfo(name = ROW_FIZZ_LABEL)
    val fizzLabel: String,
    @ColumnInfo(name = ROW_BUZZ_MULTIPLE)
    val buzzMultiple: Int,
    @ColumnInfo(name = ROW_BUZZ_LABEL)
    val buzzLabel: String,
    @ColumnInfo(name = ROW_LIMIT)
    val limit: Int,
) {
    @Ignore
    val fizzBuzzMultiple: Int = fizzMultiple * buzzMultiple
    @Ignore
    val fizzBuzzLabel: String = "$fizzLabel$buzzLabel"

    companion object {
        const val TABLE_NAME = "FizzBuzzForm"
        const val ROW_ID = "id"
        const val ROW_FIZZ_MULTIPLE = "fizzMultiple"
        const val ROW_FIZZ_LABEL = "fizzLabel"
        const val ROW_BUZZ_MULTIPLE = "buzzMultiple"
        const val ROW_BUZZ_LABEL = "buzzLabel"
        const val ROW_LIMIT = "limit"
    }
}
