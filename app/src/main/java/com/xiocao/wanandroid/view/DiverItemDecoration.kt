package com.xiocao.wanandroid.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View


open class DiverItemDecoration @JvmOverloads constructor(// 布局方向
        private var orientation: Int = VERTICAL) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    // 画笔
    private val paint: Paint = Paint()
    // 分割线颜色
    private var color: Int = 0
    // 分割线尺寸
    private var size: Int = 0

    constructor(color: Int, lineSize: Int) : this(VERTICAL) {
        setColor(color)
        setSize(lineSize)
    }

    constructor(orientation: Int, color: Int, lineSize: Int) : this(orientation) {
        setColor(color)
        setSize(lineSize)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (orientation == VERTICAL) {
            drawHorizontal(c, parent)
        } else {
            drawVertical(c, parent)
        }
    }

    /**
     * 设置分割线颜色
     *
     * @param color 颜色
     */
    fun setColor(color: Int) {
        this.color = color
        paint.color = color
    }

    fun setOrientation(orientation: Int) {
        this.orientation = orientation
    }

    /**
     * 设置分割线尺寸
     *
     * @param size 尺寸
     */
    fun setSize(size: Int) {
        this.size = size
    }

    // 绘制垂直分割线
    protected fun drawVertical(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + size

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    // 绘制水平分割线
    protected fun drawHorizontal(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + size

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (orientation == VERTICAL) {
            outRect.set(0, 0, 0, size)
        } else {
            outRect.set(0, 0, size, 0)
        }
    }

    companion object {
        /**
         * 水平方向
         */
        val HORIZONTAL = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL

        /**
         * 垂直方向
         */
        val VERTICAL = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
    }
}
