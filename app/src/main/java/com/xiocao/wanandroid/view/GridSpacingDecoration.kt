package com.xiocao.wanandroid.view

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View


class GridSpacingDecoration(private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {
    private var spanCount: Int = 0

    private fun getSpanCount(parent: RecyclerView): Int {
        // 列数
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                    .spanCount
        }
        return spanCount
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        if (spanCount == 0)
            spanCount = getSpanCount(parent)
        val position = parent.getChildAdapterPosition(view)
        val column = position % this.spanCount
        if (this.includeEdge) {
            outRect.left = this.spacing - column * this.spacing / this.spanCount
            outRect.right = (column + 1) * this.spacing / this.spanCount
        } else {
            outRect.left = column * this.spacing / this.spanCount
            outRect.right = this.spacing - (column + 1) * this.spacing / this.spanCount
        }
        if (position < this.spanCount) {
            outRect.top = this.spacing
        }
        outRect.bottom = this.spacing
    }
}
