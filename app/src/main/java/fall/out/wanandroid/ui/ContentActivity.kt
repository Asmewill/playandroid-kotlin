package fall.out.wanandroid.ui

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Owen on 2019/10/31
 */
class ContentActivity:BaseActivity() {
    private lateinit var  agentWeb:AgentWeb
    private var shareTitle=""
    private var shareUrl="http://www.baidu.com"
    private  var id=0
    private val mWebView:NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }
    override fun attachLayoutRes(): Int {
       return R.layout.activity_content
    }

    override fun initView() {
        toolbar.apply {
            title = ""//getString(R.string.loading)
            setSupportActionBar(this)//toolbar支持ActionBar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)//ToolBar左侧添加一个默认的返回图标
        }
        tv_title.text=getString(R.string.loading)
        tv_title.visibility= View.VISIBLE
        tv_title.postDelayed(object:Runnable{
            override fun run() {
                tv_title.isSelected=true
            }
        },2000)
        val layoutParams= androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.behavior= AppBarLayout.ScrollingViewBehavior()
        shareTitle=intent.getStringExtra(Constant.CONTENT_TITLE_KEY)
        shareUrl=intent.getStringExtra(Constant.CONTENT_URL_KEY)
        id=intent.getIntExtra(Constant.CONTENT_ID_KEY,0)
        agentWeb=AgentWeb.with(this).
            setAgentWebParent(cl_main,1,layoutParams)
            .useDefaultIndicator()
            .setWebView(mWebView)
            .setWebChromeClient(webChromeClient)
            .createAgentWeb().ready().go(shareUrl)
        agentWeb.webCreator.webView.settings.domStorageEnabled=true


    }
    override fun initData() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{//响应ToolBar默认的返回图标
                finish()
            }
            R.id.action_share->{
                  var intent =Intent();
                  intent.action=Intent.ACTION_SEND
                  intent.putExtra(Intent.EXTRA_TEXT,"玩Android分享【"+shareTitle+"】:"+shareUrl)
                  intent.type=Constant.CONTENT_SHARE_TYPE
                  startActivity(Intent.createChooser(intent,"分享"))

            }
            R.id.action_like->{
                addCollection()

            }
            R.id.action_browser->{
                val intent=Intent()
                intent.action="android.intent.action.VIEW"
                intent.data= Uri.parse(shareUrl)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addCollection() {
        RetrofitHelper.apiService.addCollectArticle(id).applySchedulers()
                .subscribe(OObserver(object : ApiCallBack<HttpResult<Any>> {
                    override fun onSuccess(t: HttpResult<Any>) {
                        if (t != null) {
                            showToast("加入收藏成功")
                        }
                    }
                    override fun onFailture(t: Throwable) {
                    }
                }))
    }

    private val webChromeClient=object:WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            title.let {
                tv_title.text=it
            }
        }

    }



}