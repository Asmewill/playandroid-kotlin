package fall.out.wanandroid.ui

import android.content.Intent
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.CommonUtil
import fall.out.wanandroid.adapter.SearchHistoryAdapter
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.HotkeyBean
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.SearchHistoryBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.litepal.LitePal

/**
 * Created by Owen on 2019/11/18
 */
class SearchActivity:BaseActivity() {

    private val hotkeyList=ArrayList<HotkeyBean>()
    private  val searchHistoryAdapter by lazy {
        SearchHistoryAdapter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_search
    }
    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rv_history_search.layoutManager=LinearLayoutManager(this)
      //  rv_history_search.addItemDecoration(SpaceItemDecoration(this))
        rv_history_search.adapter=searchHistoryAdapter
        searchHistoryAdapter.bindToRecyclerView(rv_history_search)
        searchHistoryAdapter.setEmptyView(R.layout.search_empty_view)
        search_history_clear_all_tv.setOnClickListener {
            deleteAll()
            searchHistoryAdapter.setNewData(null)
        }
        searchHistoryAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                searchHistoryAdapter.data[position]
                goToSearchList( searchHistoryAdapter.data[position].key)
            }

        hot_search_flow_layout.setOnTagClickListener { _, position, _ ->
            goToSearchList(hotkeyList[position].name.toString())
            true
        }

    }
    override fun initData() {
        getHotKeyList()
        queryHistoryKey()

    }
    private fun getHotKeyList() {
        RetrofitHelper.apiService.getHotKeyList().applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<List<HotkeyBean>>>{
            override fun onSuccess(t: HttpResult<List<HotkeyBean>>) {
                hotkeyList.addAll(t?.data?:ArrayList<HotkeyBean>())
                //TAGAdapter  抽象函数+泛型+构造方法
                hot_search_flow_layout.adapter=object:TagAdapter<HotkeyBean>(hotkeyList){
                    override fun getView(parent: FlowLayout?, position: Int, t: HotkeyBean?): View ?{
                        val textView=View.inflate(this@SearchActivity,R.layout.flow_layout_tv,null) as TextView
                        textView.text=t?.name
                        textView.setTextColor(CommonUtil.randomColor())
                        return textView
                    }
                }
            }
            override fun onFailture(t: Throwable) {

            }
        }))

    }

    /***
     * 保存关键词
     */
    fun saveSearchKey(key:String){
        doAsync {
            val historyBean= SearchHistoryBean()
            historyBean.key=key.trim()
            //查找列表
            val beanList=LitePal.where("key=\'"+key.trim()+"\'").find(SearchHistoryBean::class.java)
            if(beanList.size==0){
               historyBean.save()
            }else{
              LitePal.delete(SearchHistoryBean::class.java,beanList.get(0).id)
              historyBean.save()
            }
            uiThread {
                queryHistoryKey()
            }
        }
    }

    /***
     * 查找所有的历史记录
     *
     */
    fun queryHistoryKey(){
        doAsync {
            val beanList=LitePal.findAll(SearchHistoryBean::class.java)
            beanList.reverse()
            uiThread {
                searchHistoryAdapter.setNewData(beanList)
            }
        }
    }

    /***
     * 删除单个关键字
     */
    fun deleleHotKey(key:String){
        doAsync {
            val beanList=LitePal.where("key=\'"+key.trim()+"\'").find(SearchHistoryBean::class.java)
            if(beanList.size>0){
                LitePal.delete(SearchHistoryBean::class.java,beanList.get(0).id)
            }
            queryHistoryKey()
        }
    }

    fun deleteAll(){
        doAsync {
            LitePal.deleteAll(SearchHistoryBean::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search,menu)
        //必须是androidx.appcompat.widget.SearchView方能实现我们想要的搜索效果
        val searchView=menu?.findItem(R.id.action_search)?.actionView  as androidx.appcompat.widget.SearchView
        searchView.maxWidth=Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint="搜索更多干货"
        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(!TextUtils.isEmpty(p0)){
                    saveSearchKey(p0!!)
                    goToSearchList(p0)
                }
                return  false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
               return false
            }
        })
        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun goToSearchList(key:String?){
        startActivity(Intent(this@SearchActivity,CommonActivity::class.java)
            .putExtra(Constant.TYPE_KEY,Constant.Type.SEARCH_TYPE_KEY)
            .putExtra(Constant.SEARCH_WORD_KEY,key))
    }

}