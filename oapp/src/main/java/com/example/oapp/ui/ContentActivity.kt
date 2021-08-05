package com.example.oapp.ui


import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import com.example.oapp.constant.Constant
import com.example.oapp.ext.showToast
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by jsxiaoshui on 2021/6/29
 */
class ContentActivity:BaseActivity() {


    private  var collectId: Int=0
    private lateinit var shareUrl: String
    private lateinit var agentWeb:AgentWeb

    override fun attachLayoutRes(): Int {
        return R.layout.activity_content
    }

    override fun initView() {
        title=intent.extras.getString(Constant.CONTENT_TITLE)
        shareUrl=intent.extras.getString(Constant.CONTENT_URL)
        collectId=intent.extras.getInt(Constant.CONTENT_ID,0)


        toolbar?.apply {
            //this.setTitle(getString(R.string.loading))
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)//显示系统的返回键
        }
        tv_title.setText(getString(R.string.agentweb_loading))
        tv_title.visibility=View.VISIBLE
        tv_title.postDelayed({
               tv_title.isSelected=true
            }
        ,2000)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{//系统的返回键
                finish()
            }
            R.id.action_like->{
                showToast("like")
            }
            R.id.action_share->{
                showToast("share")
            }
            R.id.action_browser->{
                showToast("browser")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
        var layoutParams=CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.MATCH_PARENT)
         layoutParams.behavior=AppBarLayout.ScrollingViewBehavior()
         agentWeb = AgentWeb.with(this@ContentActivity)
             .setAgentWebParent(cl_main, layoutParams)
             .useDefaultIndicator()
             .setWebChromeClient(MyWebChromeClient(true,this@ContentActivity))
 //            .setWebChromeClient(webChromeClient)
//             .setWebView(NestedScrollAgentWebView(this@ContentActivity))//指定webview为NestedScrollAgentWebView，toolbar可以显示隐藏
             .createAgentWeb().ready().go(shareUrl)

    }

    //第一种方式，直接创建常量
    private val webChromeClient=object:WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            title?.apply {
                tv_title.setText(title)
            }
        }
    }

    //第一种方式，使用内部类方式
    private class MyWebChromeClient(private var success:Boolean,private var activity:ContentActivity):WebChromeClient(){

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if(success){
                title?.apply {
                    activity.tv_title.setText(this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        agentWeb.webLifeCycle.onResume()
    }

    override fun onPause() {
        super.onPause()
        agentWeb.webLifeCycle.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        agentWeb.webLifeCycle.onDestroy()
    }


}