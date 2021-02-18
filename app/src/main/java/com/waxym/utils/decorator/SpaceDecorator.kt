package com.waxym.utils.decorator

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

open class SpaceDecorator constructor(
    @DimenRes private val top: Int? = null,
    @DimenRes private val right: Int? = null,
    @DimenRes private val bottom: Int? = null,
    @DimenRes private val left: Int? = null,
    private val predicate: (outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) -> Boolean = { _, _, _, _ -> true }
) : RecyclerView.ItemDecoration() {

    class First(@DimenRes top: Int? = null, @DimenRes right: Int? = null, @DimenRes bottom: Int? = null, @DimenRes left: Int? = null) :
        SpaceDecorator(top, right, bottom, left, { _: Rect, view: View, parent: RecyclerView, _: RecyclerView.State ->
            val childPosition = parent.getChildAdapterPosition(view)
            0 == childPosition
        })

    class Last(@DimenRes top: Int? = null, @DimenRes right: Int? = null, @DimenRes bottom: Int? = null, @DimenRes left: Int? = null) :
        SpaceDecorator(top, right, bottom, left, { _: Rect, view: View, parent: RecyclerView, _: RecyclerView.State ->
            val childPosition = parent.getChildAdapterPosition(view)
            parent.adapter?.itemCount?.minus(1) == childPosition
        })

    class Index(@DimenRes top: Int? = null, @DimenRes right: Int? = null, @DimenRes bottom: Int? = null, @DimenRes left: Int? = null, index: Int) :
        SpaceDecorator(top, right, bottom, left, { _: Rect, view: View, parent: RecyclerView, _: RecyclerView.State ->
            val childPosition = parent.getChildAdapterPosition(view)
            index == childPosition
        })

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (predicate(outRect, view, parent, state)) {
            top?.let { outRect.top = parent.resources.getDimension(it).toInt() }
            right?.let { outRect.right = parent.resources.getDimension(it).toInt() }
            bottom?.let { outRect.bottom = parent.resources.getDimension(it).toInt() }
            left?.let { outRect.left = parent.resources.getDimension(it).toInt() }
        }
    }
}