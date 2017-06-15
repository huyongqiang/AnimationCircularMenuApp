package com.zhh.animationcircularmenuapp

/**
 * 常量类
 */
interface Constants {
    companion object {

        //以下item是根据手机480*800---》1080*1920计算的菜单弧度（菜单个数为3-10个）
        val ITEM3 = intArrayOf(-20, 0, 20)
        val ITEM4 = intArrayOf(-30, -10, 10, 30)
        val ITEM5 = intArrayOf(-40, -20, 0, 20, 40)
        val ITEM6 = intArrayOf(-50, -30, -10, 10, 30, 50)
        val ITEM7 = intArrayOf(-55, -34, -16, 0, 16, 34, 55)
        val ITEM8 = intArrayOf(-54, -36, -21, -7, 7, 21, 36, 54)
        val ITEM9 = intArrayOf(-60, -41, -26, -13, 0, 13, 26, 41, 60)
        val ITEM10 = intArrayOf(-63, -45, -31, -18, -6, 6, 18, 31, 45, 63)
        //菜单个数
        val MENUALL = arrayOf("飞猪旅行", "滴滴出行", "城市服务", "我的快递", "蚂蚁金服", "蚂蚁森林", "蚂蚁花呗")
    }
}
