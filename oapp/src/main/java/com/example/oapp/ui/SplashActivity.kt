package com.example.oapp.ui

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import com.example.oapp.ext.showToast
import com.example.oapp.utils.SettingUtil
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * Created by jsxiaoshui on 2021/6/24
 */
class SplashActivity : BaseActivity() {
    lateinit var alphaAnimation:AlphaAnimation

    override fun attachLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        alphaAnimation= AlphaAnimation(0.3f,1.0f)
       var backData= alphaAnimation.run {
            this.setAnimationListener(object:Animation.AnimationListener{
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    jumpToMain()
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }

            })
            this.duration=3000
            666
        }
        showToast("返回值："+backData)
        layout_splash.startAnimation(alphaAnimation)

    }

    private fun jumpToMain() {
        var  intent=Intent(this@SplashActivity,MainActivity::class.java)
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        finish()
    }

    override fun initData() {

    }

    override fun initColor() {
        super.initColor()
        layout_splash.setBackgroundColor(mThemeColor)
    }

}