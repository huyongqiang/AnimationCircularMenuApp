package com.zhh.animationcircularmenuapp


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


/**
 * 菜单项的范围为：3-10
 */
class HomePageMenuLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    // 菜单项的文本
    private var mItemTexts: Array<String>? = null

    private val StatusHeight: Int//状态栏高度

    init {
        StatusHeight = ScreenUtils.getStatusHeight(context)
    }


    /**
     * 设置布局的宽高，并策略menu item宽高
     */
    internal var resWidth = 0
    internal var resHeight = 0
    internal var mRadius = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //布局宽高尺寸设置为屏幕尺寸
        //设置该布局的大小
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)

        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        resHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        resWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        Log.i("zhh", resHeight.toString() + "----" + resWidth)

        // 获得半径
        mRadius = (resHeight / 2 - 2 * StatusHeight).toInt()
        //设置item尺寸
        val childSize = (mRadius * 1 / 2).toInt()
        // menu item测量模式--精确模式
        val childMode = View.MeasureSpec.EXACTLY

        for (i in 0..childCount - 1) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            var makeMeasureSpec = -1
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(childSize, childMode)
            child.measure(makeMeasureSpec, makeMeasureSpec)
        }

    }

    /**
     * item布局的角度
     */
    private var widthall: IntArray? = null

    /**
     * 设置Item的位置：第一个参数1：该参数指出当前ViewGroup的尺寸或者位置是否发生了改变
     * 2.当期绘图光标横坐标位置
     * 3.当前绘图光标纵坐标位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var left: Int
        var top: Int
        val cWidth = (mRadius * 1 / 2).toInt()
        val childCount = childCount
        // 计算，中心点到menu item中心的距离
        val tmp = (mRadius - cWidth / 2).toFloat()
        // 遍历去设置menuitem的位置
        for (i in 0..childCount - 1) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            left = (mRadius * Math.cos(Math.toRadians(widthall!![i].toDouble()))).toInt() - 65
            top = (mRadius.toDouble() - (resHeight / 2 - 2 * StatusHeight) * Math.sin(Math.toRadians(widthall!![i].toDouble())) - StatusHeight.toDouble()).toInt()
            child.layout(left, top, left + cWidth, top + cWidth)

        }

    }

    interface OnMenuItemClickListener {
        fun itemClick(view: View, pos: Int)
    }

    fun setOnMenuItemClickListener(
            mOnMenuItemClickListener: OnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener
    }


    // 菜单的个数
    private var mMenuItemCount: Int = 0

    /**
     * 设置菜单条目的图标和文本
     */
    fun setMenuItemIconsAndTexts(mItemTexts: Array<String>) {
        this.mItemTexts = mItemTexts
        this.mMenuItemCount = mItemTexts.size
        resultAngle()
        addMenuItems()
    }

    private fun resultAngle() {

        when (this.mMenuItemCount) {
            3 -> widthall = Constants.ITEM3
            4 -> widthall = Constants.ITEM4
            5 -> widthall = Constants.ITEM5
            6 -> widthall = Constants.ITEM6
            7 -> widthall = Constants.ITEM7
            8 -> widthall = Constants.ITEM8
            9 -> widthall = Constants.ITEM9
            10 -> widthall = Constants.ITEM10
            else -> {
            }
        }
        Log.i("zhh", "length-->" + widthall!!.size)

    }


    /**
     * 设置菜单条目的图标和文本
     */
    fun setMenuItemIconsAndTexts() {
        addMenuItems()
    }

    private val mMenuItemLayoutId = R.layout.homepage_item_layout


    /**
     * MenuItem的点击事件接口
     */
    private var mOnMenuItemClickListener: OnMenuItemClickListener? = null

    private var yPosition = 0f
    /**
     * 添加菜单项
     */
    private fun addMenuItems() {
        val mInflater = LayoutInflater.from(getContext())

        /**
         * 根据用户设置的参数，初始化view
         */
        for (i in 0..mMenuItemCount - 1) {
            val j = i
            val view = mInflater.inflate(mMenuItemLayoutId, this, false)

            val iv = view
                    .findViewById(R.id.homepage_pager1_item_img) as ImageView
            val tv = view
                    .findViewById(R.id.homepage_pager1_item_tv) as TextView
            iv?.setImageResource(R.mipmap.menu_ture)
            if (tv != null) {
                tv.text = mItemTexts!![i]
            }
            view.findViewById(R.id.homepage_item_layout).setOnClickListener { }
            view.findViewById(R.id.homepage_item_layout).setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    yPosition = event.y//获取按下的位置
                    iv.setImageResource(R.mipmap.menu)
                } else if (event.action == MotionEvent.ACTION_UP) {
                    iv.setImageResource(R.mipmap.menu_ture)
                    val displacement = Math.abs(yPosition - event.y)
                    //精确按下的位置做出响应
                    if (mOnMenuItemClickListener != null && displacement < 25) {
                        mOnMenuItemClickListener!!.itemClick(v, j)
                    }
                } else if (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_POINTER_UP) {
                    iv.setImageResource(R.mipmap.menu_ture)
                }
                true
            }
            addView(view)
        }
    }
}
