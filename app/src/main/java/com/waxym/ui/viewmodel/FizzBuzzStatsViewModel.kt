package com.waxym.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.annotation.ColorInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.waxym.R
import com.waxym.data.dao.FizzBuzzFormDao
import com.waxym.data.database.FizzBuzzDatabase
import com.waxym.data.model.FizzBuzzForm
import com.waxym.ui.widget.CircularStatView
import com.waxym.utils.injection.inject
import kotlinx.coroutines.launch

class FizzBuzzStatsViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context get() = getApplication()

    private val database: FizzBuzzDatabase by inject()
    private val formDao: FizzBuzzFormDao get() = database.formDao()

    private var colorPointer = 0
        get() = (field % MAX).also { field += 1 }

    private val _stats = MutableLiveData<List<UIO>>()
    val stats: LiveData<List<UIO>> = _stats

    init {
        viewModelScope.launch {
            val data: List<FizzBuzzForm> = formDao.getMostFrequent(MAX)
            val total: Float = formDao.sumHitCount()?.toFloat() ?: 0f

            val percent = mutableListOf<CircularStatView.Percent>()
            val charts = mutableListOf<UIO.Char>()
            data.forEach {
                val color = context.getColor(COLORS[colorPointer])
                percent.add(it.toPercent(color, total))
                charts.add(it.toChartUio(color))
            }
            val graph = if (total > 0f) {
                listOf(percent.toGraphUio(total))
            } else {
                listOf()
            }

            _stats.postValue(graph + charts)
        }
    }

    private fun FizzBuzzForm.toPercent(@ColorInt color: Int, total: Float): CircularStatView.Percent =
        CircularStatView.Percent(hit.toFloat() / total, color)

    private fun List<CircularStatView.Percent>.toGraphUio(total: Float): UIO.Graph = UIO.Graph(
        data = this,
        total = context.getString(R.string.label_stats_graph_total, total.toInt()),
    )

    private fun FizzBuzzForm.toChartUio(@ColorInt color: Int): UIO.Char = UIO.Char(
        color = color,
        fizzMultiple = "$fizzMultiple",
        fizzLabel = fizzLabel,
        buzzMultiple = "$buzzMultiple",
        buzzLabel = buzzLabel,
        limit = context.getString(R.string.label_stats_chart_list_start, limit),
        hit = context.getString(R.string.label_stats_chart_list_Hit, hit),
    )

    sealed class UIO {

        data class Graph(
            val data: List<CircularStatView.Percent>,
            val total: String,
        ) : UIO()

        data class Char(
            @ColorInt
            val color: Int,
            val fizzMultiple: String,
            val fizzLabel: String,
            val buzzMultiple: String,
            val buzzLabel: String,
            val limit: String,
            val hit: String,
        ) : UIO()
    }

    companion object {
        private val COLORS = listOf(
            R.color.chart_0,
            R.color.chart_1,
            R.color.chart_2,
            R.color.chart_3,
            R.color.chart_4,
            R.color.chart_5,
            R.color.chart_6,
            R.color.chart_7,
            R.color.chart_8,
            R.color.chart_9,
        )
        private val MAX = COLORS.size
    }
}