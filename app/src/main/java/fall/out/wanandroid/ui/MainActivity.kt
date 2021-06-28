package fall.out.wanandroid.ui

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationView
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.DialogUtil
import fall.out.wanandroid.Utils.Preference
import fall.out.wanandroid.Utils.SettingUtil
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.event.ColorEvent
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.UserInfoBody
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.event.LoginEvent
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity :BaseActivity() {
    private val BOTTOM_INDEX: String = "bottom_index"
    private var coinCount: Int=0
    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_KNOWLEDGE = 0x02
    private val FRAGMENT_NAVIGATION = 0x03
    private val FRAGMENT_PROJECT = 0x04
    private val FRAGMENT_WECHAT = 0x05
    private var mIndex=FRAGMENT_HOME
    private var nav_rank: ImageView?=null
    private var nav_user_rank: TextView?=null
    private var nav_user_grade: TextView?=null
    private var nav_user_id: TextView?=null
    private var nav_username: TextView?=null
    private var homeFragment:HomeFragment?=null
    private var ll_header:LinearLayout?=null
    private var knowledgeTreeFragment:KnowledgeTreeFragment?=null
    private var weChatFragment: WeChatFragment?=null
    private var navigationFragment:NavigationFragment?=null
    private var projectFragment:ProjectFragment?=null
    private var username:String by Preference(Constant.USER_NAME_KEY,"")
    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState!=null){
            mIndex=savedInstanceState?.getInt(BOTTOM_INDEX)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BOTTOM_INDEX,mIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_search->{
                //showToast("Search....")
                startActivity(Intent(this@MainActivity,SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun initView() {
        refreshColor(ColorEvent())
         toolbar.run {
             this.title=getString(R.string.app_name)
             setSupportActionBar(this)
         }
        bottom_navigation.run {
            this.labelVisibilityMode=LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            this.setOnNavigationItemSelectedListener(OnMyNavigationItemSelectedListener())

        }
        drawer_layout.run {
            var toggle=ActionBarDrawerToggle(this@MainActivity,this,toolbar,0,0)
            addDrawerListener(toggle)
            toggle.syncState()
        }
        nav_view.run {
            //原始形式
            this.setNavigationItemSelectedListener(object:NavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                    TODO("Not yet implemented")
                }

            });
            //lambda1
            this.setNavigationItemSelectedListener {it:MenuItem->
                when(it.itemId){
                    R.id.nav_score->{}
                    R.id.nav_collect->{}


                }

                true
            }
            //lambda2
            this.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_score ->{//我的积分
                        if(isLogin){
                         startActivity(Intent(this@MainActivity,ScoreActivity::class.java).putExtra(Constant.COIN_COUNT,coinCount))
                        }else{
                          startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        }
                    }
                    R.id.nav_collect ->{//我的收藏
                        if(isLogin){
                            startActivity(Intent(this@MainActivity,CommonActivity::class.java)
                                .putExtra(Constant.TYPE_KEY,Constant.Type.COLLECT_TYPE_KEY))
                        }else{
                            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        }
                    }
                    R.id.nav_setting ->{//系统设置
                        startActivity(Intent(this@MainActivity,SettingActivity::class.java))

                    }
                    R.id.nav_todo ->{//TODO
                        startActivity(Intent(this@MainActivity,ToDoActivity::class.java))
                    }
                    R.id.nav_night_mode ->{//夜间模式
                        window.setWindowAnimations(R.style.WindowAnimationFadeInOut)//放在这里不会0.5s黑屏
                       if(SettingUtil.getIsNightMode()){
                           SettingUtil.setIsNightMode(false)
                           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                       }else{
                           SettingUtil.setIsNightMode(true)
                           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                       }

                        recreate()
                    }
                    R.id.nav_about_us ->{//关于我们
                           startActivity(Intent(this@MainActivity,CommonActivity::class.java)
                            .putExtra(Constant.TYPE_KEY,Constant.Type.ABOUT_US_TYPE_KEY))
                    }
                    R.id.nav_logout ->{//退出登录
                        DialogUtil.getConfimDialog(this@MainActivity,"确认退出登录?",object:DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                loginOut()
                            }
                        }).show()
                    }
                }
              // return@setNavigationItemSelectedListener   true
                    //lambda括号里面的函数最后一行就是这个函数的返回值
                 true
            }
            ll_header=this.getHeaderView(0).findViewById<LinearLayout>(R.id.ll_header)
            nav_username=this.getHeaderView(0).findViewById<TextView>(R.id.tv_username)
            nav_user_id=this.getHeaderView(0).findViewById<TextView>(R.id.tv_user_id)
            nav_user_grade=this.getHeaderView(0).findViewById<TextView>(R.id.tv_user_grade)
            nav_user_rank=this.getHeaderView(0).findViewById<TextView>(R.id.tv_user_rank)
            nav_rank=getHeaderView(0).findViewById<ImageView>(R.id.iv_rank)
            menu.findItem(R.id.nav_logout).setVisible(isLogin)
        }

        if(isLogin){
            nav_username?.setText(username)
        }else{
            nav_username?.setText("去登录")
        }
        floating_action_btn.run {
            this.setOnClickListener {
                when(it.id){

                }
            }
        }
        showFragment(mIndex)

        nav_rank?.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity,PointRankListActivity::class.java))
            }
        })
        ll_header?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(!isLogin){
                    startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                }
            }
        })
    }
    override fun recreate() {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (homeFragment != null) {
                fragmentTransaction.remove(homeFragment!!)
            }
            if (knowledgeTreeFragment != null) {
                fragmentTransaction.remove(knowledgeTreeFragment!!)
            }
            if (navigationFragment != null) {
                fragmentTransaction.remove(navigationFragment!!)
            }
            if (projectFragment != null) {
                fragmentTransaction.remove(projectFragment!!)
            }
            if (weChatFragment != null) {
                fragmentTransaction.remove(weChatFragment!!)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.recreate()
    }

    private fun loginOut() {
        RetrofitHelper.apiService.logout().applySchedulers().subscribe(OObserver(object :ApiCallBack<HttpResult<Any>>{
            override fun onSuccess(t: HttpResult<Any>) {
                showToast("已退出登录")
                Preference.clearPreference()
                isLogin=false
                EventBus.getDefault().post(LoginEvent(false))
            }

            override fun onFailture(t: Throwable) {

            }
        }))

    }

    override fun initData() {
        getUserInfo()

    }
     fun showFragment(mIndex:Int) {
        val transaction=supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        this.mIndex=mIndex
        when(mIndex){
            FRAGMENT_HOME->{
                toolbar.title=getString(R.string.app_name)
                if(homeFragment==null){
                    homeFragment=HomeFragment.getInstance()
                    transaction.add(R.id.container,homeFragment!!)
                }else{
                    transaction.show(homeFragment!!)
                }

            }
            FRAGMENT_KNOWLEDGE->{
                toolbar.title=getString(R.string.knowledge_system)
                if(knowledgeTreeFragment==null){
                    knowledgeTreeFragment=KnowledgeTreeFragment.getInstance()
                    transaction.add(R.id.container,knowledgeTreeFragment!!)
                }else{
                    transaction.show(knowledgeTreeFragment!!)
                }

            }
            FRAGMENT_WECHAT->{
                toolbar.title=getString(R.string.wechat)
                if(weChatFragment==null){
                    weChatFragment=WeChatFragment.getInstance()
                    transaction.add(R.id.container,weChatFragment!!)
                }else{
                    transaction.show(weChatFragment!!)
                }

            }
            FRAGMENT_NAVIGATION->{
                toolbar.title=getString(R.string.navigation)
                if(navigationFragment==null){
                    navigationFragment=NavigationFragment.getInstance()
                    transaction.add(R.id.container,navigationFragment!!)
                }else{
                    transaction.show(navigationFragment!!)
                }
            }
            FRAGMENT_PROJECT->{
                toolbar.title=getString(R.string.project)
                if(projectFragment==null){
                    projectFragment=ProjectFragment.getInstance()
                    transaction.add(R.id.container,projectFragment!!)
                }else{
                    transaction.show(projectFragment!!)
                }
            }
        }
        transaction.commit()

    }

    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let {
            transaction.hide(it)
        }
        knowledgeTreeFragment?.let {
            transaction.hide(it)
        }
        weChatFragment?.let {
            transaction.hide(it)
        }
        navigationFragment?.let {
            transaction.hide(it)
        }
        projectFragment?.let {
            transaction.hide(it)
        }
    }

    /***
     * 必须加入Inner,否则无法访问showFragment()方法
     * kotlin重写接口的方式
     */
    inner class OnMyNavigationItemSelectedListener:BottomNavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when(item.itemId){
                R.id.action_home ->{
                     showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.action_knowledge_system ->{
                    showFragment(FRAGMENT_KNOWLEDGE)
                    true
                }
                R.id.action_wechat ->{
                    showFragment(FRAGMENT_WECHAT)
                    true
                }
                R.id.action_navigation ->{
                    showFragment(FRAGMENT_NAVIGATION)
                    true
                }
                R.id.action_project ->{
                    showFragment(FRAGMENT_PROJECT)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    @Subscribe
    fun loginEvent(event: LoginEvent) {
        if(event.isLogin){
            nav_username?.setText(username)
            nav_view.menu.findItem(R.id.nav_logout).isVisible=true
            homeFragment?.requestBanner()
            getUserInfo()
        }else{
            nav_username?.setText("去登录")
            nav_view.menu.findItem(R.id.nav_logout).isVisible=false
            nav_user_grade?.setText("--")
            nav_user_rank?.setText("--")
        }
    }

    fun getUserInfo(){
        RetrofitHelper.apiService.getUserInfo().applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<UserInfoBody>>{
            override fun onSuccess(t: HttpResult<UserInfoBody>) {
                if(t.data!=null){
                    nav_user_grade?.setText(t.data?.level+"")
                    nav_user_rank?.setText(t.data?.rank.toString())
                    coinCount=t.data?.coinCount?:0
                }

            }

            override fun onFailture(t: Throwable) {

            }

        }))
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent){
        initColor()
        nav_view.getHeaderView(0).setBackgroundColor(mThemeColor)
        floating_action_btn.backgroundTintList=ColorStateList.valueOf(mThemeColor)

    }


}
