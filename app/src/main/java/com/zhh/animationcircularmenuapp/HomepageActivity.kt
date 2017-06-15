package com.zhh.animationcircularmenuapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast


/**
 * Update by zhh on 2017/06/14.
 * 个人
 * csdn：http://blog.csdn.net/zhh_csdn_ard
 * devstore：http://www.devstore.cn/user_home/zhanghai_ardapp.html
 * github:https://github.com/seastoneard/
 */
class HomepageActivity : AppCompatActivity() {

    private var homePageMenuLayout: HomePageMenuLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_layout)
        initLayout()
    }

    private fun initLayout() {
        homePageMenuLayout = findViewById(R.id.homepage_layout) as HomePageMenuLayout
        //加载菜单列表
        homePageMenuLayout!!.setMenuItemIconsAndTexts(Constants.MENUALL)
        //才点动画初始
        SwitchAnimationUtil().startAnimation(homePageMenuLayout!!, SwitchAnimationUtil.AnimationType.ROTATE)
        //事件监听
        homePageMenuLayout!!.setOnMenuItemClickListener(object : HomePageMenuLayout.OnMenuItemClickListener {
            override fun itemClick(view: View, pos: Int) {
                Toast.makeText(this@HomepageActivity, Constants.MENUALL[pos], Toast.LENGTH_SHORT).show()
            }
        })
    }

}
