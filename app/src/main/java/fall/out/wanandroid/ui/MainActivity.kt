package fall.out.wanandroid.ui

import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseMvpActivity
import fall.out.wanandroid.bean.UserInfoBody
import fall.out.wanandroid.mvp.contract.MainContract
import fall.out.wanandroid.mvp.presenter.MainPresenter
import fall.out.wanandroid.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import showToast

class MainActivity : BaseMvpActivity<MainContract.View,MainContract.Presenter>(),MainContract.View {
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
    private var knowledgeTreeFragment:KnowledgeTreeFragment?=null
    private var weChatFragment: WeChatFragment?=null
    private var navigationFragment:NavigationFragment?=null
    private var projectFragment:ProjectFragment?=null


    override fun showLogoutSuccess(success: Boolean) {

    }
    override fun showUserInfo(bean: UserInfoBody) {

    }

    override fun createPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
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
            //写法二
            this.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_score ->{
                        showToast("我的积分")
                    }
                    R.id.nav_collect ->{
                        showToast("我的收藏")
                    }
                    R.id.nav_setting ->{
                        showToast("系统设置")
                    }
                    R.id.nav_todo ->{
                        showToast("TODO")
                    }
                    R.id.nav_night_mode ->{
                        showToast("夜间模式")
                    }
                    R.id.nav_about_us ->{
                        showToast("关于我们")
                    }
                    R.id.nav_logout ->{
                        showToast("退出登录")
                    }
                }
             //  return@setNavigationItemSelectedListener   true
                true
            }
            nav_username=this.getHeaderView(0).findViewById<TextView>(R.id.tv_username)
            nav_user_id=this.getHeaderView(0).findViewById<TextView>(R.id.tv_user_id)
            nav_user_grade=this.getHeaderView(0).findViewById<TextView>(R.id.tv_user_grade)
            nav_user_rank=this.getHeaderView(0).findViewById<TextView>(R.id.tv_user_rank)
            nav_rank=getHeaderView(0).findViewById<ImageView>(R.id.iv_rank)
            menu.findItem(R.id.nav_logout).setVisible(isLogin)
        }
        floating_action_btn.run {
            this.setOnClickListener {
                when(it.id){

                }
            }
        }
        showFragment(mIndex)

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
                    transaction.show(homeFragment!!)
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
                toolbar.title=getString(R.string.wechat)
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

    override fun start() {

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
}
