# AnimationCircularMenuApp
<!-- Baidu Button BEGIN -->
<div id="article_content" class="article_content tracking-ad" data-mod=popu_307  data-dsm = "post" >

<p><span style="color:rgb(51,51,51); font-family:Arial; font-size:14px"><strong><span style="font-size:12px"><br>
</span></strong></span></p>
<p><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px">前言</span></strong></span><span style="color:rgb(51,51,51); font-family:Arial; font-size:12px"><strong>：</strong></span><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:12px">基于AS的采用Kotlin语言开发的动画渐入的弧形菜单</span></strong></span></p>
<p><span style="color:rgb(51,51,51); font-family:Arial"><strong><br>
</strong></span></p>
<p><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px">效果:</span></strong></span></p>
<p style="text-align:center"><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px"><img src="http://img.blog.csdn.net/20170615100440289?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhoX2NzZG5fYXJk/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" width="360" height="580" alt=""><br>
</span></strong></span></p>
<p><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px"><br>
</span></strong></span></p>
<p><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-size:14px">开发环境：AndroidStudio2.2.1&#43;gradle-2.14.1</span></span><br>
</strong></span></p>
<p><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px"><br>
</span></strong></span></p>
<p><span><span><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-size:14px"><span style="font-family:SimHei"><strong>涉及知识：</strong></span><span style="font-family:SimHei">1.自定义控件，</span><span style="font-family:SimHei">2.事件分发等</span></span></span><br>
</strong></span></span></span></p>
<p><span><span><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial; font-size:14px"><span style="font-family:SimHei"><br>
</span></span></span></strong></span></span></span></p>
<p><span><span><span style="color:rgb(51,51,51); font-family:Arial; font-weight:bold"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:SimHei"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:SimHei"><span style="font-size:14px">部分代码：</span></span></span><br>
</span></span></span></span></span></p>
<p><span><span><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial; font-size:14px"><span style="font-family:SimHei">Activity:</span></span></span></strong></span></span></span></p>
<p><span><span><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial; font-size:14px"><span style="font-family:SimHei"></span></span></span></strong></span></span></span><pre name="code" class="java">
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
        homePageMenuLayout!!.setOnMenuItemClickListener(object : HomePageMenuLayout.
            OnMenuItemClickListener {
                override fun itemClick(view: View, pos: Int) {
                    Toast.makeText(this@HomepageActivity, Constants.MENUALL[pos], 
                        Toast.LENGTH_SHORT).show()
            }
        })
    }

}
</pre><br>
动画类：</p>
<p><span><span><span style="color:rgb(51,51,51); font-family:Arial"><strong><span style="font-size:18px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial; font-size:14px"><span style="font-family:SimHei"></span></span></span></strong></span></span></span><pre name="code" class="java">
class SwitchAnimationUtil {
    private var mOrderIndex = 0
    private val mDelay = 80
    private val mDuration = 500

    fun startAnimation(root: View, type: AnimationType) {
        bindAnimation(root, 0, type)
    }

    private fun bindAnimation(view: View, depth: Int, type: AnimationType) {

        if (view is ViewGroup) {
            val group = view

            for (i in 0..group.childCount - 1) {
                bindAnimation(group.getChildAt(i), depth + 1, type)
            }

        } else {
            runAnimation(view, (mDelay * mOrderIndex).toLong(), type)
            mOrderIndex++
        }
    }

    private fun runAnimation(view: View, delay: Long, type: AnimationType) {
        when (type) {
            SwitchAnimationUtil.AnimationType.ROTATE -> runRotateAnimation(view, delay)
            SwitchAnimationUtil.AnimationType.ALPHA -> runAlphaAnimation(view, delay)
            else -> {
            }
        }
    }

    private fun runAlphaAnimation(view: View, delay: Long) {
        view.alpha = 0f
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        objectAnimator.startDelay = delay
        objectAnimator.duration = mDuration.toLong()
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.start()
    }

    private fun runRotateAnimation(view: View, delay: Long) {
        view.alpha = 0f
        val set = AnimatorSet()
        val objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 0f)
        val objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        val objectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        val objectAnimator4 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)

        objectAnimator2.interpolator = AccelerateInterpolator(1.0f)
        objectAnimator3.interpolator = AccelerateInterpolator(1.0f)

        set.duration = mDuration.toLong()
        set.playTogether(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4)
        set.startDelay = delay
        set.start()
    }

    enum class AnimationType {
        ALPHA, ROTATE
    }
}
</pre><br>
<br>
</p> 
</div>

<!-- Baidu Button END -->

