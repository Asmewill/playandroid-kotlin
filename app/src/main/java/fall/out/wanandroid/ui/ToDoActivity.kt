package fall.out.wanandroid.ui

import android.content.Intent
import android.os.Build
import android.view.*
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.TodoPagerAdapter
import fall.out.wanandroid.adapter.TodoPopupAdapter
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.TodoTypeBean
import fall.out.wanandroid.constant.Constant
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Owen on 2019/11/4
 */
class ToDoActivity:BaseActivity() {
    private var mPop: PopupWindow?=null
    private var listBean=ArrayList<TodoTypeBean>()

    private val todoPagerAdapter by lazy {
        TodoPagerAdapter(supportFragmentManager)//在Activity中使用
    }
    private val todoPopupAdapter by lazy {
        TodoPopupAdapter()
    }

    override fun attachLayoutRes(): Int {
       return  R.layout.activity_todo
    }
    override fun initView() {
        toolbar?.title="TODO"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewPager?.adapter=todoPagerAdapter
        bottom_navigation.labelVisibilityMode=1
        bottom_navigation.setOnNavigationItemSelectedListener(object :BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when(p0.itemId){
                    R.id.action_notodo->{
                        viewPager.setCurrentItem(0)
                    }
                    R.id.action_completed->{
                        viewPager.setCurrentItem(1)
                    }
                }
                return true
            }
        })
        floating_action_btn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(
                    Intent(this@ToDoActivity,CommonActivity::class.java)
                        .putExtra(Constant.TYPE_KEY,Constant.Type.ADD_TODO_TYPE_KEY)

                )

            }
        })
    }

    override fun initData() {

    }

    fun showPop(){
        if(mPop==null){
           initPop()
        }
        if(mPop?.isShowing==true){
            mPop?.dismiss()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPop?.showAsDropDown(toolbar, -fall.out.wanandroid.Utils.DisplayManager.dip2px(5f), 0, Gravity.END)
        } else {
            mPop?.showAtLocation(toolbar, Gravity.BOTTOM, -fall.out.wanandroid.Utils.DisplayManager.dip2px(5F), 0)
        }
    }

    private fun initPop() {
        val recyclerView=layoutInflater.inflate(R.layout.layout_popup_todo,null) as RecyclerView
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=todoPopupAdapter
        todoPopupAdapter.setNewData(getListBean())
        mPop=PopupWindow()
        mPop?.contentView=recyclerView
        mPop?.width=ViewGroup.LayoutParams.WRAP_CONTENT
        mPop?.height=ViewGroup.LayoutParams.WRAP_CONTENT
        mPop?.isOutsideTouchable=true
        todoPopupAdapter.bindToRecyclerView(recyclerView)
        todoPopupAdapter.setOnItemClickListener(object :BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var item=adapter?.data?.get(position) as TodoTypeBean
                item.type=position
                if(viewPager.currentItem==0){
                    item.playType="listnotdo"
                }else{
                    item.playType="listdone"
                }
                adapter.data.forEachIndexed { index, any ->
                    val item=any as TodoTypeBean
                    item.isSelect=index==position
                }
                adapter.notifyDataSetChanged()
                if(mPop!=null&&mPop?.isShowing()==true){
                    mPop?.dismiss()
                }
                EventBus.getDefault().post(item)
            }
        })
    }

    fun getListBean():List<TodoTypeBean>{
        listBean.add(TodoTypeBean(1,"只用这一个",true))
        listBean.add(TodoTypeBean(2,"工作",false))
        listBean.add(TodoTypeBean(3,"学习",false))
        listBean.add(TodoTypeBean(4,"生活",false))
        return  listBean
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_todo,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
            R.id.action_todo_type->{
                showPop()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}