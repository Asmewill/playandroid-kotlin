package fall.out.wanandroid.ui

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by Owen on 2019/10/4
 */
class SplashActivity : BaseActivity() {
    override fun initData() {

    }
    private lateinit var alphaAnimation: AlphaAnimation

    override fun attachLayoutRes(): Int {
       return R.layout.activity_splash
    }

    override fun initView() {
        alphaAnimation=AlphaAnimation(0.3f,1.0f)
        alphaAnimation.run  {
            this.setAnimationListener(object: Animation.AnimationListener{
                override fun onAnimationRepeat(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    jumpToMain()
                }

                override fun onAnimationStart(p0: Animation?) {

                }

            })
            this.duration=3000
        }
        layout_splash.startAnimation(alphaAnimation)

    }

    override fun initColor() {
        super.initColor()
        layout_splash.setBackgroundColor(mThemeColor)
    }




    private fun jumpToMain() {
        var intent = Intent(SplashActivity@this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}