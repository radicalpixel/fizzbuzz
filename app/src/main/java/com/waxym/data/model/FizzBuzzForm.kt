package com.waxym.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.waxym.data.model.FizzBuzzForm.Companion.COL_BUZZ_LABEL
import com.waxym.data.model.FizzBuzzForm.Companion.COL_BUZZ_MULTIPLE
import com.waxym.data.model.FizzBuzzForm.Companion.COL_FIZZ_LABEL
import com.waxym.data.model.FizzBuzzForm.Companion.COL_FIZZ_MULTIPLE
import com.waxym.data.model.FizzBuzzForm.Companion.COL_LIMIT

@Entity(tableName = FizzBuzzForm.TABLE_NAME, primaryKeys = [COL_FIZZ_MULTIPLE, COL_FIZZ_LABEL, COL_BUZZ_MULTIPLE, COL_BUZZ_LABEL, COL_LIMIT])
data class FizzBuzzForm(
    @ColumnInfo(name = COL_FIZZ_MULTIPLE)
    val fizzMultiple: Int,
    @ColumnInfo(name = COL_FIZZ_LABEL)
    val fizzLabel: String,
    @ColumnInfo(name = COL_BUZZ_MULTIPLE)
    val buzzMultiple: Int,
    @ColumnInfo(name = COL_BUZZ_LABEL)
    val buzzLabel: String,
    @ColumnInfo(name = COL_LIMIT)
    val limit: Int,
    @ColumnInfo(name = COL_COUNT)
    var count: Int,
) {
    companion object {
        const val TABLE_NAME = "FizzBuzzForm"
        const val COL_FIZZ_MULTIPLE = "col_fizzMultiple"
        const val COL_FIZZ_LABEL = "col_fizzLabel"
        const val COL_BUZZ_MULTIPLE = "col_buzzMultiple"
        const val COL_BUZZ_LABEL = "col_buzzLabel"
        const val COL_LIMIT = "col_limit"
        const val COL_COUNT = "col_count"
    }
}
