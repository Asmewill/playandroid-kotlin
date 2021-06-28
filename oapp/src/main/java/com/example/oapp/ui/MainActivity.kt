package com.example.oapp.ui

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import com.example.oapp.expand.showToast
import com.example.oapp.ui.fragment.*
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by jsxiaoshui on 2021/6/25
 */
class MainActivity :BaseActivity() {
    //Fragment导航索引
    private val FRAGMENT_HOME=0
    private val FRAGMENT_KNOWLEDGE=1
    private val FRAGMENT_WECHAT=2
    private val FRAGMENT_NAVIGATION=3
    private val FRAGMENT_PROJECT=4

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search->
                showToast("搜索")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        //let的基础用法
        toolbar?.let{
            setTitle("oapp")
            setSupportActionBar(it)
            return@let 88//函数最后一行或者return 表达式表示函数的返回值
        }
        //run的基础用法
        bottom_navigation.run {
            this.labelVisibilityMode=LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            this.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.action_home->{
                        showToast("首页")
                        showFragment(FRAGMENT_HOME)
                    }
                    R.id.action_knowledge_system->{
                        showToast("知识体系")
                         showFragment(FRAGMENT_KNOWLEDGE)
                    }
                    R.id.action_wechat->{
                        showToast("公众号")
                        showFragment(FRAGMENT_WECHAT)
                    }
                    R.id.action_navigation->{
                        showToast("导航")
                        showFragment(FRAGMENT_NAVIGATION)
                    }
                    R.id.action_project->{
                        showToast("项目")
                        showFragment(FRAGMENT_PROJECT)
                    }
                }
                 true  //lambda最后一行就是返回值
            }
             888 //函数最后一行或者return 表达式表示函数的返回值
        }
        //with的基础用法[原始写法]
        with(drawer_layout,{
            var toggle=ActionBarDrawerToggle(this@MainActivity,this,toolbar,0,0)
            this.addDrawerListener(toggle)
            toggle.syncState()
            return@with 8888
        })


    }
    private  var  homeFragment:HomeFragment?=null
    private  var  knowledgeFragment: KnowledgeFragment?=null
    private  var weChatFragment: WeChatFragment?=null
    private  var  navigationFragment: NavigationFragment?=null
    private  var projectFragment: ProjectFragment?=null

    private fun showFragment(mIndex:Int){
        val transaction=supportFragmentManager.beginTransaction();
        hideAllFragment(transaction)
        when(mIndex){
            FRAGMENT_HOME->{
                if(homeFragment==null){
                    homeFragment=HomeFragment()
                    transaction.add(R.id.container,homeFragment!!)
                }else{
                    transaction.show(homeFragment!!)
                }
            }
            FRAGMENT_KNOWLEDGE->{
                if(knowledgeFragment==null){
                    knowledgeFragment= KnowledgeFragment()
                    transaction.add(R.id.container,knowledgeFragment!!)
                }else{
                    transaction.show(knowledgeFragment!!)
                }

            }
            FRAGMENT_WECHAT->{
                if(weChatFragment==null){
                    weChatFragment= WeChatFragment()
                    transaction.add(R.id.container,weChatFragment!!)
                }else{
                    transaction.show(weChatFragment!!)
                }

            }
            FRAGMENT_NAVIGATION->{
                if(navigationFragment==null){
                    navigationFragment= NavigationFragment()
                    transaction.add(R.id.container,navigationFragment!!)
                }else{
                    transaction.show(navigationFragment!!)
                }
            }
            FRAGMENT_PROJECT->{
                if(projectFragment==null){
                    projectFragment= ProjectFragment()
                    transaction.add(R.id.container,projectFragment!!)
                }else{
                    transaction.show(projectFragment!!)
                }
            }
            else->{

            }
        }
        transaction.commit()


    }

    private fun hideAllFragment(trasaction: FragmentTransaction) {
        homeFragment?.let {
            trasaction.hide(homeFragment!!)
        }
        knowledgeFragment?.let {
            trasaction.hide(knowledgeFragment!!)
        }
        weChatFragment?.let {
            trasaction.hide(weChatFragment!!)
        }
        navigationFragment?.let {
            trasaction.hide(navigationFragment!!)
        }
        projectFragment?.run {
            trasaction.hide(projectFragment!!)
        }
    }

    override fun initData() {
    }
}